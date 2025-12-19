# Gerenciamento de Eventos e Ingressos - Aplicação Distribuída

[![Java](https://img.shields.io/badge/Java-17-red?logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-green?logo=mongodb&logoColor=white)](https://www.mongodb.com/atlas)
[![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.x-ff6600?logo=rabbitmq&logoColor=white)](https://www.rabbitmq.com/)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue?logo=docker)](https://www.docker.com/)
[![AWS](https://img.shields.io/badge/AWS-EC2-orange?logo=amazonaws)](https://aws.amazon.com/ec2/)

Sistema distribuído para gerenciamento de eventos e ingressos com mensageria assíncrona. Dois microsserviços integrados via RabbitMQ que permitem criar eventos, comprar ingressos e receber confirmações por email, com deployment em AWS EC2 e MongoDB Atlas.

---

## Quick Start

```bash
# 1. Clonar o repositório
git clone https://github.com/guilhermemarch/events-and-tickets-management.git
cd events-and-tickets-management

# 2. Configurar MongoDB Atlas
# Criar cluster gratuito em https://www.mongodb.com/atlas
# Criar database: scholarship-ship-desafio3
# Copiar connection string

# 3. Atualizar application.properties nos dois microsserviços
# event-manager/src/main/resources/application.properties
# ticket-manager/src/main/resources/application.properties
spring.data.mongodb.uri=mongodb+srv://user:password@cluster.mongodb.net/scholarship-ship-desafio3

# 4. Iniciar com Docker Compose
docker-compose up --build

# 5. Testar a aplicação
# event-manager: http://localhost:8080
# ticket-manager: http://localhost:8081
```

**Importar coleção do Postman:** [Download aqui](https://drive.google.com/file/d/123U02DzwTt_dfb-IZbtcB1ZWubq4rUDN/view?usp=sharing)

---

## Funcionalidades

### Gerenciamento de Eventos
- CRUD completo: Criar, buscar, listar, atualizar e deletar eventos
- Ordenação alfabética: Listar eventos ordenados por nome
- Informações detalhadas: Nome, cidade, data e capacidade

### Gerenciamento de Ingressos
- Compra de ingressos: Criar ingressos vinculados a eventos
- Cancelamento: Cancelar ingressos adquiridos
- Atualização: Modificar informações do ingresso
- Busca: Por ID ou por evento
- Rastreamento de status: ACTIVE, CANCELLED

### Notificações por Email
- Confirmação automática: Email ao criar ingresso
- Mensageria assíncrona: RabbitMQ para processamento
- Detalhes completos: Evento, data, cidade, ID do ticket

### Deployment em Produção
- AWS EC2: Alta disponibilidade e escalabilidade
- MongoDB Atlas: Database gerenciado na nuvem
- Docker: Containerização completa

---

## Arquitetura

### Arquitetura Distribuída

```mermaid
graph TB
    subgraph "Client Layer"
        User[Cliente/Postman]
    end
    
    subgraph "Application Layer"
        subgraph "Event Manager - Port 8080"
            EventAPI[Event API<br/>REST Controller]
            EventService[Event Service]
            EventRepo[Event Repository]
        end
        
        subgraph "Ticket Manager - Port 8081"
            TicketAPI[Ticket API<br/>REST Controller]
            TicketService[Ticket Service]
            TicketRepo[Ticket Repository]
            EmailPublisher[Email Publisher]
        end
    end
    
    subgraph "Messaging Layer"
        RabbitMQ[RabbitMQ<br/>Port 5672/15672]
        Queue[ticket.email.queue]
        EmailConsumer[Email Consumer]
    end
    
    subgraph "Data Layer"
        MongoDB[(MongoDB Atlas<br/>scholarship-ship-desafio3)]
    end
    
    User -->|HTTP| EventAPI
    User -->|HTTP| TicketAPI
    
    EventAPI --> EventService
    EventService --> EventRepo
    EventRepo -->|CRUD| MongoDB
    
    TicketAPI --> TicketService
    TicketService --> TicketRepo
    TicketRepo -->|CRUD| MongoDB
    TicketService --> EmailPublisher
    
    EmailPublisher -->|Publish| RabbitMQ
    RabbitMQ --> Queue
    Queue --> EmailConsumer
    EmailConsumer -.->|Send Email| User
```

### Arquitetura de Mensageria (RabbitMQ)

```mermaid
graph LR
    subgraph "Ticket Manager"
        CreateTicket[Create Ticket<br/>Controller]
        Publisher[Email Publisher<br/>Service]
    end
    
    subgraph "RabbitMQ Broker"
        Exchange[Direct Exchange<br/>ticket.email.exchange]
        Queue[Queue<br/>ticket.email.queue]
    end
    
    subgraph "Email Consumer"
        Consumer[Email Listener<br/>@RabbitListener]
        EmailService[Email Service<br/>Template Builder]
        SMTP[SMTP Client]
    end
    
    CreateTicket -->|1. Save ticket| MongoDB[(MongoDB)]
    CreateTicket -->|2. Publish event| Publisher
    Publisher -->|3. Send message| Exchange
    Exchange -->|routing key| Queue
    Queue -->|4. Consume| Consumer
    Consumer -->|5. Build message| EmailService
    EmailService -->|6. Send email| SMTP
```

### Fluxo de Criação de Ingresso com Email

```mermaid
sequenceDiagram
    actor User
    participant TicketAPI as Ticket Manager API
    participant Service as Ticket Service
    participant DB as MongoDB Atlas
    participant Publisher as Email Publisher
    participant MQ as RabbitMQ
    participant Consumer as Email Consumer
    participant Email as Email Service

    User->>TicketAPI: POST /create-ticket {eventId, userName, userEmail}
    activate TicketAPI

    TicketAPI->>Service: createTicket(ticketDTO)
    activate Service

    Service->>DB: Validate event exists
    DB-->>Service: Event found

    Service->>Service: Create ticket entity (status: ACTIVE)
    Service->>DB: Save ticket
    DB-->>Service: Ticket saved (id generated)

    Service->>Publisher: publishTicketCreated(ticket)
    activate Publisher
    Publisher->>MQ: Send message to ticket.email.queue
    MQ-->>Publisher: Message queued
    deactivate Publisher

    Service-->>TicketAPI: Ticket created
    deactivate Service

    TicketAPI-->>User: 201 Created {ticket}
    deactivate TicketAPI

    Note over MQ,Consumer: Processamento Assíncrono

    MQ->>Consumer: Consume message
    activate Consumer

    Consumer->>Email: buildEmailContent(ticket)
    activate Email
    Email->>Email: Format HTML email
    Email-->>Consumer: Email content

    Consumer->>Email: sendEmail(to, subject, body)
    Email-->>Consumer: Email sent
    deactivate Email

    Consumer-->>User: Email de confirmação
    deactivate Consumer
```

---

## Tecnologias

### Backend

| Tecnologia | Versão | Propósito |
|------------|--------|-----------|
| Java | 17 | Linguagem de programação |
| Spring Boot | 3.x | Framework de aplicação |
| Spring Web | 6.x | APIs REST |
| Spring Data MongoDB | 4.x | Persistência NoSQL |
| Spring AMQP | 3.x | Integração RabbitMQ |
| Maven | 3.9 | Build e dependências |
| Lombok | 1.18.x | Redução de boilerplate |

### Infraestrutura

| Serviço | Propósito |
|---------|-----------|
| MongoDB Atlas | Database gerenciado na nuvem (tier gratuito disponível) |
| RabbitMQ | Message broker para comunicação assíncrona |
| Docker Compose | Orquestração de containers |
| AWS EC2 | Servidor em produção |

---

### Instalação Local

```bash
# 1. Clonar repositório
git clone https://github.com/guilhermemarch/events-and-tickets-management.git
cd events-and-tickets-management

# 2. Configurar MongoDB Atlas em AMBOS application.properties

# event-manager/src/main/resources/application.properties
spring.data.mongodb.uri=mongodb+srv://user:password@cluster.mongodb.net/scholarship-ship-desafio3

# ticket-manager/src/main/resources/application.properties
spring.data.mongodb.uri=mongodb+srv://user:password@cluster.mongodb.net/scholarship-ship-desafio3

# 3. Iniciar com Docker Compose
docker-compose up --build
```

---

## Uso

### Verificar Serviços

```bash
# Ver status dos containers
docker-compose ps

# Acessar logs
docker-compose logs -f event-manager
docker-compose logs -f ticket-manager
```

### Exemplos de Uso

#### 1. Criar Evento

```bash
curl -X POST http://localhost:8080/create-event \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Show da Xuxa",
    "city": "São Paulo",
    "date": "2024-12-30T21:00:00",
    "capacity": 5000
  }'
```

#### 2. Listar Todos os Eventos

```bash
curl http://localhost:8080/get-all-events
```

#### 3. Criar Ingresso (com Email Automático)

```bash
curl -X POST http://localhost:8081/create-ticket \
  -H "Content-Type: application/json" \
  -d '{
    "eventId": "677693637a48fe0b12324e15",
    "userName": "Guilherme Marschall",
    "userEmail": "guilherme@example.com"
  }'
```

**Email enviado:**
```
Olá, Guilherme Marschall! Seu ticket para o evento 'Show da Xuxa' na cidade de São Paulo, 
na data 2024-12-30T21:00:00, foi criado com sucesso.

Detalhes do Ticket:
Evento: Show da Xuxa
Cidade: São Paulo
Data: 2024-12-30T21:00:00
ID do Ticket: 677693637a48fe0b12324e16
Status: ACTIVE

Agradecemos por comprar conosco!
```

---

## API Endpoints

### Event Manager (Port 8080)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/create-event` | Criar evento |
| `GET` | `/get-event/{id}` | Buscar evento por ID |
| `GET` | `/get-all-events` | Listar todos os eventos |
| `GET` | `/get-all-events-sorted` | Listar eventos ordenados (A-Z) |
| `PUT` | `/update-event/{id}` | Atualizar evento |
| `DELETE` | `/delete-event/{id}` | Deletar evento |

### Ticket Manager (Port 8081)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/create-ticket` | Criar ingresso (envia email) |
| `GET` | `/get-ticket/{id}` | Buscar ingresso por ID |
| `GET` | `/get-tickets-event/{eventId}` | Listar ingressos de um evento |
| `PUT` | `/update-ticket/{id}` | Atualizar ingresso |
| `DELETE` | `/cancel-ticket/{id}` | Cancelar ingresso |

---

## AWS Deployment

### Pré-requisitos AWS

- Conta AWS (tier gratuito disponível)
- EC2 instance (t2.micro recomendado)
- Security Group configurado (portas 8080, 8081, 5672, 15672)
