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
- Docker (para a base de dados PostgreSQL via Docker Compose, e para construir a imagem da aplicação)
- (opcional) [Minikube](https://minikube.sigs.k8s.io/) + `kubectl`, para correr em Kubernetes localmente
- (opcional) [Terraform](https://developer.hashicorp.com/terraform) >= 1.5, para provisionar os recursos Kubernetes como infraestrutura como código

## Como executar (modo desenvolvimento)

O projeto usa Spring Boot Docker Compose, que sobe automaticamente a base de dados definida em `compose.yaml` ao iniciar a aplicação.

```bash
./gradlew bootRun
```

A aplicação fica disponível em `http://localhost:8080`.

## Docker

A imagem é construída com um `Dockerfile` multi-stage (compila com JDK, corre com JRE, mais leve).

```bash
docker build -t inventory-api .
```

Para correr a aplicação e a base de dados juntas, com a app já dentro de um container (ao contrário do modo `bootRun`, que só faz isso automaticamente em desenvolvimento):

```bash
docker compose up --build
```

A aplicação fica disponível em `http://localhost:8080`.

## Kubernetes (local, via Minikube)

Os manifests em `k8s/` (Secret, Deployments e Services para a app e para o Postgres) permitem correr o projeto num cluster Kubernetes local.

```bash
minikube start --driver=docker
docker build -t inventory-api-kotlin-app .
minikube image load inventory-api-kotlin-app:latest

kubectl apply -f k8s/
kubectl rollout status deployment/inventory-api

kubectl port-forward svc/inventory-api 8080:8080
```

A aplicação fica disponível em `http://localhost:8080`.

> Nota: `imagePullPolicy: Never` no `k8s/app-deployment.yaml` só faz sentido em ambiente local, com a imagem carregada manualmente no Minikube — não se aplica a um cluster real (ex.: GKE), onde a imagem viria de um registo remoto.

## Infraestrutura como código (Terraform)

A pasta `terraform/` recria os mesmos recursos (Secret, Deployments, Services e o `PersistentVolumeClaim` do Postgres) como infraestrutura gerida por Terraform, em vez de `kubectl apply` manual.

```bash
minikube start --driver=docker
docker build -t inventory-api-kotlin-app .
minikube image load inventory-api-kotlin-app:latest

cd terraform
terraform init
terraform plan
terraform apply
```

Não aplicar `terraform/` e `kubectl apply -f k8s/` ao mesmo tempo sobre o mesmo cluster — vão colidir por tentarem criar recursos com o mesmo nome.

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

As configurações da aplicação encontram-se em `src/main/resources/application.properties`.

A ligação à base de dados PostgreSQL é definida de forma diferente consoante o modo de execução:
- `./gradlew bootRun` — gerida automaticamente pelo Spring Boot Docker Compose, a partir de `compose.yaml`
- `docker compose up` — variáveis de ambiente do serviço `app` em `compose.yaml`
- Kubernetes / Terraform — variáveis de ambiente injetadas a partir do Secret `postgres-credentials`, definido em `k8s/postgres-secret.yaml` ou `terraform/main.tf`
