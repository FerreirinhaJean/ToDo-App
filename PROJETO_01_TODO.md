# 📌 Projeto 1 — Sistema de Gestão de Tarefas (To-Do Profissional)

## 🧠 Objetivo
Desenvolver uma **API REST profissional** para gerenciamento de tarefas pessoais, com autenticação segura, regras de negócio claras e boas práticas de arquitetura usando **Java + Spring Boot**.

Este projeto é voltado para **aprendizado prático**, simulando um sistema real utilizado em empresas.

---

## 🎯 Objetivos de Aprendizado

- Criar APIs REST com Spring Boot
- Aplicar arquitetura em camadas
- Implementar autenticação com JWT
- Trabalhar com Spring Data JPA
- Criar DTOs e validações
- Tratar exceções globalmente
- Escrever testes unitários
- Organizar um projeto para portfólio

---

## 🛠 Stack Tecnológica

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- Bean Validation
- PostgreSQL
- Flyway ou Liquibase
- Lombok
- OpenAPI (Swagger)
- JUnit 5 + Mockito
- Docker (opcional)

---

## 🏗 Arquitetura do Projeto

```
com.seuprojeto.todo
│
├── controller
│ ├── AuthController
│ └── TaskController
│
├── service
│ ├── AuthService
│ ├── TaskService
│ └── UserService
│
├── repository
│ ├── UserRepository
│ └── TaskRepository
│
├── entity
│ ├── User
│ └── Task
│
├── dto
│ ├── request
│ └── response
│
├── mapper
│
├── exception
│ ├── GlobalExceptionHandler
│ ├── BusinessException
│ ├── ResourceNotFoundException
│ └── UnauthorizedException
│
├── config
│ ├── security
│ └── swagger
│
└── TodoApplication.java
```

---

## 🗂 Modelagem do Domínio

### 👤 User
| Campo | Tipo |
|---|---|
| id | Long |
| name | String |
| email | String (único) |
| password | String (criptografada) |
| createdAt | LocalDateTime |

---

### ✅ Task
| Campo | Tipo |
|---|---|
| id | Long |
| title | String |
| description | String |
| status | TaskStatus |
| createdAt | LocalDateTime |
| updatedAt | LocalDateTime |
| user | User |

---

### 📘 Enum: TaskStatus
- PENDING
- COMPLETED

---

## 🔐 Segurança

- Autenticação baseada em **JWT**
- Endpoints públicos:
    - `POST /auth/register`
    - `POST /auth/login`
- Endpoints protegidos:
    - `/tasks/**`
- Usuário só pode acessar **suas próprias tarefas**
- Senhas criptografadas com **BCrypt**

---

## 📑 Endpoints da API

### 🔓 Autenticação

#### ➤ Registrar usuário

##### POST /auth/register

**Request**
```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "123456"
}
```

##### Response

```json
{
"id": 1,
"name": "João Silva",
"email": "joao@email.com"
}
```

#### ➤ Login

##### POST /auth/login
**Request**
```json
{
  "email": "joao@email.com",
  "password": "123456"
}
```

##### Response

```json
{
  "token": "JWT_TOKEN"
}
```

---

### 🔒 Tarefas (JWT obrigatório)

#### ➤ Criar tarefa

##### POST /tasks

#### Request

```json
{
"title": "Estudar Spring Boot",
"description": "Estudar Spring Security e JPA"
}
```

#### ➤ Listar tarefas (com paginação e filtro)
##### GET /tasks?page=0&size=10&status=PENDING

#### ➤ Buscar tarefa por ID
##### GET /tasks/{id}

#### ➤ Atualizar tarefa
##### PUT /tasks/{id}

#### Request
```json
{
"title": "Estudar Spring Boot Avançado",
"description": "JWT e testes"
}
```

#### ➤ Concluir tarefa
##### PATCH /tasks/{id}/complete

#### ➤ Deletar tarefa
##### DELETE /tasks/{id}

### 🔁 Regras de Negócio

* Email do usuário deve ser único
* Título da tarefa é obrigatório
* Tarefa sempre inicia com status PENDING
* Usuário só pode acessar suas próprias tarefas
* Não é permitido alterar tarefas concluídas
* Não é permitido concluir uma tarefa já concluída

### 🧾 DTOs
#### UserRegisterRequest
- name
- email
- password

#### LoginRequest
- email
- password

#### TaskCreateRequest
- title
- description

#### TaskUpdateRequest
- title
- description

#### TaskResponse
- id
- title
- description
- status
- createdAt

### ⚠ Tratamento de Erros
| Situação               | HTTP |
|------------------------|------|
| Erro de validação      | 400  |
| Não autenticado        | 401  |
| Acesso negado          | 403  |
| Recurso nao encontrado | 404  |
| Regra de negocio       | 409  |

### Exemplo de resposta
```json
{
"timestamp": "2026-01-14T10:00:00",
"status": 400,
"message": "Title is required"
}
```

### 🧪 Testes Obrigatórios
* Testes unitários dos Services
* Teste de autenticação
* Testes de regras de negócio
* Testes de exceções

### 📄 README do Projeto

#### Deve conter:
- Descrição do projeto
- Tecnologias utilizadas
- Como rodar o projeto
- Configuração do banco
- Endpoints principais
- Autenticação JWT

### 🚀 Critérios de Conclusão
- API funcionando
- Segurança implementada
- Código organizado
- Testes básicos criados
- README completo
- Commits bem definidos

### 🔜 Evoluções Futuras (Opcional)
- Refresh Token
- Recuperação de senha
- Ordenação avançada de tarefas
- Deploy em cloud