<h1 align="center">ToDo App</h1>

![Status](https://img.shields.io/badge/Status-In_Development-yellow?style=for-the-badge)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Flyway](https://img.shields.io/badge/-Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)

## 📌 About the Project

A professional **Task Management REST API** built with **Java and Spring Boot**, featuring **JWT-based authentication**,
**user-level access control**, and **clean architecture best practices**, simulating a real-world enterprise
application.

This project is focused on **hands-on learning** and **portfolio presentation**.

---

## 🛠 Technologies Used

- Java 21+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- Bean Validation
- PostgreSQL
- Flyway
- Lombok
- OpenAPI (Swagger)
- JUnit 5
- Mockito
- Docker (optional)

---

## 🏗 Project Architecture

```text
com.github.FerreirinhaJean.ToDo_App
├── config
│   ├── AuditingConfiguration.java
│   ├── OpenApiConfiguration.java
│   └── SecurityConfiguration.java
├── controller
│   ├── AuthController.java
│   ├── TaskController.java
│   └── view
│       └── LoginViewController.java
├── dto
│   ├── request
│   │   ├── TaskRequestDTO.java
│   │   └── UserRequestDTO.java
│   └── response
│       ├── ErrorResponseDTO.java
│       ├── FieldErrorDTO.java
│       ├── TaskResponseDTO.java
│       └── UserResponseDTO.java
├── entity
│   ├── enums
│   │   └── TaskStatus.java
│   ├── Task.java
│   └── User.java
├── exception
│   ├── BusinessException.java
│   ├── DuplicatedRegisterException.java
│   └── GlobalExceptionHandler.java
├── repository
│   ├── specs
│   │   └── TaskSpecification.java
│   ├── TaskRepository.java
│   └── UserRepository.java
├── security
│   ├── AuthenticationProvider.java
│   ├── JwtAuthConverter.java
│   └── UserPrincipal.java
├── service
│   ├── TaskService.java
│   └── UserService.java
└── ToDoAppApplication.java
```

---

## 🔐 Authentication & Security
* JWT-based authentication
* Passwords encrypted using **BCrypt**
* Public endpoints:
  * ```POST /auth/register```
  * ```/login```
* Protected endpoints:
  * ```/tasks/**```
* Users can only access their own tasks

---

## 🐳 How to Run the Project
```bash
docker-compose up --build
```

---

## 📄 API Documentation

After running the application, access the automatically generated documentation at:

* [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 📜 License

This project is licensed under the MIT license.

## 👤 Author

* Jean Gabriel Ferreira
* Github: [@FerreirinhaJean](https://github.com/FerreirinhaJean)