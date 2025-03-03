# Kafka Orders

## Introduction
Kafka Orders is a microservices-based system built with Spring Boot, Java 17, MongoDB, and Kafka (using Kraft for broker management). The project follows a hexagonal architecture and implements event sourcing to track and reconstruct order events.

## Overview
The system consists of two microservices:

1. **Order Service**: Manages order creation and confirmation.
2. **Shipping Service**: Processes confirmed orders and updates their shipping status.

## Order Workflow
- A new order is placed (`CREATED` state).
- The order is confirmed via an API call (`CONFIRMED` state), triggering a Kafka event.
- The Shipping Service consumes the event and updates the order to `SHIPPED`.
- The order is then marked as `DELIVERED` once the delivery is completed.

## Features
- **Event-driven architecture** using Kafka.
- **Event Sourcing** to reconstruct past events.
- **Hexagonal Architecture** for better separation of concerns.
- **MongoDB** as the database.
- **Kraft mode** for Kafka broker management (no ZooKeeper needed).

## API Endpoints

### Order Service
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/orders/place` | `POST` | Place a new order |
| `/orders/confirm/{orderId}` | `PUT` | Confirm an order |
| `/backup/deleteAll` | `POST` | Delete all stored events |
| `/backup/recreateAllEvents` | `POST` | Recreate all events from Kafka logs |

### Shipping Service
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/shipping/{orderId}/ship` | `POST` | Update an order to `SHIPPED` state |
| `/shipping/{orderId}/deliver` | `POST` | Update an order to `DELIVERED` state |

## Technologies Used
- **Spring Boot** (lombok, jackson, Kafka, Data MongoDB)
- **Java 17**
- **Kafka (Kraft mode)**
- **MongoDB**
- **Docker (for containerization)**

## Getting Started
### Prerequisites
- Docker & Docker Compose
- Java 17
- Maven

### Running the Application
1. Start Kafka (Kraft mode) and MongoDB using Docker Compose:
   ```sh
   docker-compose up -d
   ```
2. Build and run the Order and Shipping services:
   ```sh
   mvn clean install
   java -jar order-service/target/order-service.jar
   java -jar shipping-service/target/shipping-service.jar
   ```
3. Use Postman or Curl to interact with the API.

## Event Sourcing
- When all events are deleted (`/backup/deleteAll`), the system can reconstruct them from Kafka logs using `/backup/recreateAllEvents`.
- The event consumer dynamically processes the event stream to recreate the database state.

## License
MIT License