# Inventory API

API REST para gestão de inventário (produtos e categorias), construída com Kotlin e Spring Boot.

## Tecnologias

- Kotlin + Spring Boot 3.5
- Spring Data JPA
- Spring Security
- Spring Validation
- PostgreSQL
- springdoc-openapi (Swagger UI)
- Gradle (Kotlin DSL)
- Java 21

## Pré-requisitos

- JDK 21
- Docker (para a base de dados PostgreSQL via Docker Compose)

## Como executar

O projeto usa Spring Boot Docker Compose, que sobe automaticamente a base de dados definida em `compose.yaml` ao iniciar a aplicação.

```bash
./gradlew bootRun
```

A aplicação fica disponível em `http://localhost:8080`.

## Documentação da API

Com a aplicação a correr, a documentação Swagger UI fica disponível em:

```
http://localhost:8080/swagger-ui.html
```

## Endpoints principais

### Produtos (`/products`)

| Método | Endpoint        | Descrição                  |
|--------|-----------------|-----------------------------|
| GET    | `/products`     | Lista todos os produtos     |
| GET    | `/products/{id}`| Obtém um produto por ID     |
| POST   | `/products`     | Cria um novo produto        |
| PUT    | `/products/{id}`| Atualiza um produto         |
| DELETE | `/products/{id}`| Remove um produto           |

### Categorias (`/categories`)

| Método | Endpoint       | Descrição                   |
|--------|----------------|-------------------------------|
| GET    | `/categories`  | Lista todas as categorias     |
| POST   | `/categories`  | Cria uma nova categoria       |

## Testes

```bash
./gradlew test
```

## Configuração

As configurações da aplicação encontram-se em `src/main/resources/application.properties`. A ligação à base de dados PostgreSQL é definida em `compose.yaml`.
