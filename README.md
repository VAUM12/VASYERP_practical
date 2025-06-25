# Employee Management System — VASYERP Practical Task

This is a Spring Boot-based Employee Management API developed as part of the VASYERP practical assessment. It includes secure role-based access, Swagger documentation, exception handling, and uses an in-memory H2 database.

---

## ✅ Features

- 🔐 **Spring Security with JWT**  
  Role-based access (`ROLE_ADMIN`, `ROLE_USER`) with JWT authentication and authorization.

- 🧾 **Exception Handling**  
  Centralized and consistent exception responses using `ApiResponse<T>`.

- 💾 **H2 Database (in-memory)**  
  Auto-configured with schema auto-generation and dummy data on startup.  
  DB name: `employee_db`.

- 📄 **Swagger/OpenAPI Docs**  
  Fully documented endpoints using Swagger annotations, accessible via browser.

- 🌐 **Port Configuration**  
  - Application: `http://localhost:8080`
  - Swagger UI: `http://localhost:8080/swagger-ui/index.html`
  - H2 Console: `http://localhost:8080/h2-console`

---

## 🛠 Tech Stack

- Spring Boot 3
- Spring Security
- JWT
- H2 Database
- Swagger (SpringDoc)
- Maven

---

## 🔐 Roles and Access

| Endpoint                    | Access Role     |
|----------------------------|------------------|
| `/auth/register`           | Public           |
| `/auth/login`              | Public           |
| `/api/employees`           | USER, ADMIN      |
| `/api/employees/{id}`      | USER, ADMIN      |
| `/api/employees` (POST)    | ADMIN only       |
| `/api/employees/{id}` (PUT, DELETE) | ADMIN only |
| `/api/employees/salary-above-average` | ADMIN only |
| `/api/employees/department-count`     | ADMIN only |

---

## 🧪 Dummy Users (Auto-Seeded)

| Role   | Email             | Password     |
|--------|-------------------|--------------|
| Admin  | `admin@gmail.com` | `Hello@1234` |
| User   | `user@gmail.com`  | `Hello@1234` |

---

## 🔄 Employee Seeding

On application startup, the following employees are added automatically (if none exist):

- John Doe — IT — 50000
- Jane Smith — HR — 60000
- Alex Brown — Finance — 45000

---

## 🔄 H2 Database Setup

| Setting         | Value                      |
|-----------------|----------------------------|
| JDBC URL        | `jdbc:h2:mem:employee_db`  |
| Console URL     | `http://localhost:8080/h2-console` |
| Username        | `sa`                       |
| Password        | *(leave blank)*            |

Ensure that the JDBC URL in the H2 console matches:  
`jdbc:h2:mem:employee_db`

---

## 🧪 Testing with Swagger

Once app is running, navigate to:
http://localhost:8080/swagger-ui/index.html

Use `/auth/login` to generate a JWT token, then click **Authorize 🔐** and paste your token.


