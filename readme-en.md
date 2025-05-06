

# Events and Tickets Management

## Clone the Project

To clone the repository, run the command below:

```bash
git clone https://github.com/guilhermemarch/events-and-tickets-management.git
```

---

## Overview

This project consists of two main microservices:

- **event-manager**: Manages events (CRUD operations).
- **ticket-manager**: Manages tickets, linking them to events.

The microservices communicate via RabbitMQ for asynchronous messaging and use Docker to simplify the development environment.

---

## Dependencies

Ensure the following tools are installed:

- Docker and Docker Compose
- Java 17+
- Maven
- MongoDB Atlas

---

## Setup and Startup with Docker Compose

### `docker-compose.yml` File

Use the `docker-compose.yml` file to start the services.

### Execution Commands

1. Make sure you're in the project's root directory.
2. Run the following command:
   ```bash
   docker-compose up --build
   ```
3. Access the services:
   - RabbitMQ Management: [http://localhost:15672](http://localhost:15672) (User/Password: guest/guest)
   - event-manager: [http://localhost:8080](http://localhost:8080)
   - ticket-manager: [http://localhost:8081](http://localhost:8081)
4. Create the database named `scholarship-ship-desafio3` in MongoDB Atlas and ensure the connection is active.
5. Download the Postman collection for testing: [Postman Collection](https://drive.google.com/file/d/123U02DzwTt_dfb-IZbtcB1ZWubq4rUDN/view?usp=sharing).
6. The project has also been deployed to AWS, ensuring high availability and scalability. If you need to access the deployed environment, update the IP and port according to the AWS IP address. (AWS IP: http://3.21.159.74)

---

## Project Structure

```plaintext
├── ms-event-manager
│   ├── src/main/java/ -- event microservice
│   ├── Dockerfile
│
├── ms-ticket-manager
│   ├── src/main/java/ -- ticket microservice
│   ├── Dockerfile
│
├── docker-compose.yml
└── README.md
```

---

## Endpoints

### event-manager

| Method | Endpoint             | Description                    |
|--------|----------------------|--------------------------------|
| POST   | `/create-event`      | Create an event                |
| GET    | `/get-event/{id}`    | Get event by ID                |
| GET    | `/get-all-events`    | List all events                |
| PUT    | `/update-event/{id}` | Update event by ID             |
| DELETE | `/delete-event/{id}` | Delete event by ID             |
| GET    | `/get-all-events-sorted` | List all events alphabetically |

### ticket-manager

| Method | Endpoint                  | Description                      |
|--------|---------------------------|----------------------------------|
| POST   | `/create-ticket`          | Create a ticket                  |
| GET    | `/get-ticket/{id}`        | Get ticket by ID                 |
| DELETE | `/cancel-ticket/{id}`     | Cancel ticket by ID              |
| PUT    | `/update-ticket/{id}`     | Update ticket by ID              |
| GET    | `/get-tickets-event/{id}` | Get tickets by event ID          |

---

## Messaging with RabbitMQ

The microservices communicate via RabbitMQ to send confirmation emails. Example:

- When a ticket is created, the `ticket-manager` publishes a message to RabbitMQ, and the `emailConsumer` sends the email.

### Example Message:

```
Hello, Guilherme Marschall! Your ticket for the event 'Show da Xuxa' in São Paulo on 2024-12-30T21:00:00 has been successfully created.

Ticket Details:
Event: Show da Xuxa
City: São Paulo
Date: 2024-12-30T21:00:00
Ticket ID: 677693637a48fe0b12324e16
Status: ACTIVE

Thank you for purchasing with us!
```
