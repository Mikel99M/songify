# Songify - Music Management System
A music catalog management system designed with a modular domain architecture that handles complex relationships between songs, albums, and artists while maintaining strict logic encapsulation.

## Quick Start with Docker
The easiest way to run the application and all its dependencies is using Docker Compose.

Have Docker Desktop installed and running.

Clone the repository:

```bash
git clone https://github.com/Mikel99M/songify.git
cd songify

```

Build and start all services:

```Bash
docker-compose up --build
```
Access the application:

Backend API: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui/index.html

## Technical Stack
Backend: Java 17, Spring Boot 3.x

Persistence: Spring Data JPA, Hibernate

Database: PostgreSQL 

Mapping: MapStruct 

Testing: JUnit 5, Mockito, MockMvc, Testcontainers (PostgreSQL), AssertJ

DevOps: Docker, Docker Compose

Documentation: Swagger / OpenAPI

## Features
Modular Domain Design: Independent modules for Songs, Albums, Artists, and Genres with strict boundary control.

Advanced Data Retrieval: Optimized database communication using JPA Projections and EntityGraphs to ensure high performance and solve the N+1 problem.

Relational Integrity: Complex management of Many-to-Many and One-to-Many relationships between artists, their albums, and tracks.

Automated Assignments: Built-in logic for automatic genre assignment and metadata synchronization during song creation.

Robust Testing: Extensive test suite covering unit logic and integration flows with real database instances using Testcontainers.

## Architecture & Deployment
The project is built as a Modular Monolith using Domain-Driven Design (DDD) principles to prevent tight coupling and ensure maintainability.

## Infrastructure Layout:
Backend: Spring Boot application structured around Domain Facades, which serve as the only entry points to business logic.

Encapsulation: Implementation details (Repositories and Services) are hidden within packages, exposing only DTOs and Facade interfaces.

Storage: PostgreSQL for persistent storage

Retrievers & Adders: Separation of Read and Write concerns within the domain to simplify service logic.

Deployment: Fully containerized with Docker, ready for cloud environments like AWS.
