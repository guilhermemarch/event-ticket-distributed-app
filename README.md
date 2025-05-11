# event-ticket-distributed-app

## Clone o Projeto

Para clonar o repositório, execute o comando abaixo:

```bash
git clone https://github.com/guilhermemarch/events-and-tickets-management.git
```

---

## Visão Geral

Este projeto é composto por dois microsserviços principais:

- **event-manager**: Gerencia os eventos (CRUD).
- **ticket-manager**: Gerencia os ingressos, vinculando-os aos eventos.

Os microsserviços são integrados via RabbitMQ para mensagens assíncronas e utilizam Docker para simplificar o ambiente de desenvolvimento.

---

## Dependências

Certifique-se de que as seguintes ferramentas estão instaladas:

- Docker e Docker Compose
- Java 17+
- Maven
- MongoDB Atlas

---

## Configuração e Inicialização com Docker Compose

### Arquivo `docker-compose.yml`

Utilize o arquivo `docker-compose.yml` para inicializar os serviços.

### Comandos para Executar

1. Certifique-se de que está no diretório raiz do projeto.
2. Execute o comando abaixo:
   ```bash
   docker-compose up --build
   ```
3. Acesse os serviços:
   - RabbitMQ Management: [http://localhost:15672](http://localhost:15672) (Usuário/Senha: guest/guest)
   - event-manager: [http://localhost:8080](http://localhost:8080)
   - ticket-manager: [http://localhost:8081](http://localhost:8081)
4. Crie a base de dados chamada `scholarship-ship-desafio3` no MongoDB Atlas e assegure que a conexão esteja ativa.
5. Faça o download dos métodos para testes no Postman: [Coleção de metodos PostMan](https://drive.google.com/file/d/123U02DzwTt_dfb-IZbtcB1ZWubq4rUDN/view?usp=sharing).
6. O projeto também foi implantado na AWS, garantindo alta disponibilidade e escalabilidade. Caso seja necessario acessar esse ambiente, é necessario alterar o endereço e porta de acordo com o IP no AWS. (IP AWS: http://3.21.159.74)

---

## Estrutura do Projeto

```plaintext
├── ms-event-manager
│   ├── src/main/java/ -- microsserviço de eventos
│   ├── Dockerfile
│
├── ms-ticket-manager
│   ├── src/main/java/ -- microsserviço de ingressos
│   ├── Dockerfile
│
├── docker-compose.yml
└── README.md
```

---

## Endpoints

### event-manager

| Método | Endpoint             | Descrição               |
| ------ | -------------------- | ----------------------- |
| POST   | `/create-event`      | Criar evento            |
| GET    | `/get-event/{id}`    | Buscar evento por ID    |
| GET    | `/get-all-events`    | Listar todos os eventos |
| PUT    | `/update-event/{id}` | Atualizar evento por ID |
| DELETE | `/delete-event/{id}` | Deletar evento por ID   |
| GET | `/get-all-events-sorted` | Listar todos os eventos por ordem alfabética  |

### ticket-manager

| Método | Endpoint                   | Descrição                  |
| ------ | -------------------------- | -------------------------- |
| POST   | `/create-ticket`           | Criar ingresso             |
| GET    | `/get-ticket/{id}`         | Buscar ingresso por ID     |
| DELETE | `/cancel-ticket/{id}`      | Cancelar ingresso por ID   |
| PUT    | `/update-ticket/{id}`      | Atualizar ingresso por ID    |
| GET | `/get-tickets-event/{id}`     | Buscar ingressos por ID de evento   |


---

## Mensageria com RabbitMQ

Os microsserviços se comunicam via RabbitMQ para enviar um email de confirmação. Exemplo:

- Quando um ingresso é criado, o `ticket-manager` publica uma mensagem no RabbitMQ e o `emailConsumer` finaliza o email.

### Exemplo de mensagem:

```
Olá, Guilherme Marschall! Seu ticket para o evento 'Show da Xuxa' na cidade de São Paulo, na data 2024-12-30T21:00:00, foi criado com sucesso.

Detalhes do Ticket:
Evento: Show da Xuxa
Cidade: São Paulo
Data: 2024-12-30T21:00:00
ID do Ticket: 677693637a48fe0b12324e16
Status: ACTIVE

Agradecemos por comprar conosco!
```

---
