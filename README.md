# Ava task

## Getting Started

Clone repository or download and extract zip.

### Prerequisites

```
Docker Comunity 2.0
```

### Installing

Open terminal, navigate to downloaded folder and execute command:

```
docker-compose up --build -d
```

Docker will start both services with init script for database.

## Running the tests

There is a collection of prepared API calls in ava-postman.json. Collection should be imported into postman, and run.

## Documentation

API documentation can be found on:
```
http://localhost:8081/api/v1/swagger-ui.html
http://localhost:8081/api/v1/v2/api-docs
```