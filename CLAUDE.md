# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

UniversitySystem is a Student Information Management System built with Spring Boot 2.7.18 and MyBatis.

## Tech Stack

- Java 1.8
- Spring Boot 2.7.18
- MyBatis 2.3.2
- MySQL (runtime), H2 (testing)
- Maven

## Commands

```bash
# Build
mvn clean package

# Run application
mvn spring-boot:run

# Run tests
mvn test

# Run a single test class
mvn test -Dtest=StudentControllerTest
```

## Architecture

Standard layered architecture:
```
Controller -> Service -> DAO (MyBatis Mapper)
```

Key components:
- **REST API**: Base path `/api/students` via `StudentController`
- **Service Layer**: `StudentService` interface with `StudentServiceImpl`
- **DAO Layer**: MyBatis mapper interfaces with XML mapper files
- **Exception Handling**: `GlobalExceptionHandler` with `StudentNotFoundException`
- **MyBatis Config**: `@MapperScan("com.university.dao")` in `MyBatisConfig`

## Database

- MySQL at `localhost:3306/university`
- MyBatis XML mappers in `src/main/resources/mapper/`
- Auto underscore-to-camelcase mapping enabled
- Logs SQL to stdout (`logging.level.com.university.dao: DEBUG`)

## Package Structure

| Package | Purpose |
|---------|---------|
| `controller` | REST endpoints |
| `service`/`service/impl` | Business logic |
| `dao` | MyBatis mapper interfaces |
| `entity` | Domain objects |
| `dto` | Request/response objects |
| `config` | Configuration classes |
