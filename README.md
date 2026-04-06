# 💼 Job Board API

A production-ready **REST API** for managing job postings and applications, built with **Spring Boot 4**, secured with **Spring Security + JWT**, database migrations with **Flyway**, and documented with **Swagger UI**.

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.3 |
| Security | Spring Security + JWT (jjwt 0.12.3) |
| Database | MySQL 8+ |
| Migrations | Flyway (flyway-mysql) |
| ORM | Spring Data JPA / Hibernate |
| Mapping | MapStruct 1.5.5 |
| Boilerplate | Lombok |
| API Docs | SpringDoc OpenAPI (Swagger UI) 2.8.6 |
| Validation | Spring Boot Validation |
| Build | Maven |

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/jobintech/
│   │   ├── config/          # Security, Swagger, JWT config
│   │   ├── controller/      # REST controllers
│   │   ├── dto/             # Request/Response DTOs
│   │   ├── entity/          # JPA entities
│   │   ├── mapper/          # MapStruct mappers
│   │   ├── repository/      # Spring Data repositories
│   │   ├── security/        # JWT filter, UserDetailsService
│   │   └── service/         # Business logic
│   └── resources/
│       ├── application.properties
│       └── db/migration/    # Flyway SQL migrations (V1__, V2__...)
└── test/                    # Unit & integration tests
```

---

## ⚙️ Prerequisites

- Java 21+
- Maven 3.9+
- MySQL 8+ running locally

---

## 🛠️ Setup & Run

### 1. Clone the repository

```bash
git clone https://github.com/ZinebEs-saih/job-application-system.git
cd job-application-system
```

### 2. Configure the database

Create the database (Flyway will create it automatically if it doesn't exist):

```sql
CREATE DATABASE jobboard;
```

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jobboard?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true

app.jwt.secret=your-very-secret-key-minimum-256-bits
app.jwt.expiration=86400000
```

### 3. Run the application

```bash
./mvnw spring-boot:run
```

Flyway will automatically apply all migrations on startup. ✅

---

## 📖 API Documentation (Swagger UI)

Once the app is running, open your browser:

```
http://localhost:8080/swagger-ui.html
```

---

## 🔐 Authentication Flow

This API uses **stateless JWT authentication**.

### Step 1 — Register

```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "zineb",
  "email": "zineb@example.com",
  "password": "securepassword"
}
```

### Step 2 — Login & get token

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "zineb",
  "password": "securepassword"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Step 3 — Use the token

Add to every protected request:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## 📋 API Endpoints

### 🔓 Public Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT token |
| GET | `/api/jobs` | Browse all open job postings |
| GET | `/api/jobs/{id}` | Get job details |

### 🔒 Protected Endpoints (requires JWT)

| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/applications/job/{jobId}` | Apply for a job |
| GET | `/api/applications/my` | View my applications |
| PUT | `/api/applications/{id}/status` | Update application status |

### 🛡️ Admin Only

| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/jobs` | Create a new job posting |
| PUT | `/api/jobs/{id}` | Update a job posting |
| DELETE | `/api/jobs/{id}` | Delete a job posting |
| GET | `/api/applications/job/{jobId}` | View all applicants for a job |

---

## 🧪 Testing with Swagger

1. Go to `http://localhost:8080/swagger-ui.html`
2. Call `POST /api/auth/login` → copy the token
3. Click **Authorize 🔒** at the top right
4. Enter: `Bearer <your-token>`
5. All protected endpoints are now unlocked ✅

## 🧪 Testing with Postman

Import the following environment variables in Postman:

| Variable | Value |
|----------|-------|
| `base_url` | `http://localhost:8080` |
| `token` | *(set after login)* |

Set the Authorization header on protected requests:
```
Authorization: Bearer {{token}}
```

---

## 🗄️ Database Migrations (Flyway)

Migrations are located in `src/main/resources/db/migration/` and run automatically on startup.

| File | Description |
|------|-------------|
| `V1__create_users_table.sql` | Users table with roles |
| `V2__create_jobs_table.sql` | Jobs table |
| `V3__create_applications_table.sql` | Applications table |

To run migrations manually:

```bash
./mvnw flyway:migrate
```

To clean and re-run (⚠️ dev only):

```bash
./mvnw flyway:clean flyway:migrate
```

---

## 🧹 Running Tests

```bash
./mvnw test
```

---

## 👩‍💻 Author

**Zineb Es-saih**  
🔗 [github.com/ZinebEs-saih](https://github.com/ZinebEs-saih)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
