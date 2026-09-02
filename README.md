# Journal App

A backend REST API built with Spring Boot for managing personal journal entries.

This project provides secure user authentication, journal entry management, caching,
sentiment analysis, weather information, email functionality, and asynchronous
processing using Kafka.

---

## 🚀 Features

- User registration and login
- JWT-based authentication
- Spring Security
- Create, read, update, and delete journal entries
- User-specific journal entries
- Admin functionality
- MongoDB database integration
- Redis caching
- Apache Kafka integration
- Sentiment analysis
- Weather API integration
- Email notifications
- Scheduled tasks
- Unit and integration testing

---

## 🛠️ Tech Stack

| Technology | Usage |
|------------|-------|
| Java | Programming Language |
| Spring Boot | Backend Framework |
| Spring Security | Authentication & Authorization |
| JWT | Token-based Authentication |
| MongoDB | Database |
| Spring Data MongoDB | Database Access |
| Redis | Caching |
| Apache Kafka | Asynchronous Processing |
| Spring Mail | Email Service |
| Maven | Build Tool |
| JUnit 5 | Testing |
| Mockito | Unit Testing |

---

## 📁 Project Structure

```text
journalApp/
│
├── .mvn/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── net/engineeringdigest/journalApp/
│   │   │       ├── api/
│   │   │       ├── cache/
│   │   │       ├── config/
│   │   │       ├── constants/
│   │   │       ├── controllers/
│   │   │       ├── entity/
│   │   │       ├── enums/
│   │   │       ├── filter/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── scheduler/
│   │   │       ├── service/
│   │   │       └── utilis/
│   │   │
│   │   └── resources/
│   │       ├── application.yaml
│   │       ├── application-dev.yaml
│   │       ├── application-prod.yaml
│   │       └── logback.xml
│   │
│   └── test/
│       └── java/
│
├── .gitignore
├── .gitattributes
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## ⚙️ Configuration

The application uses environment variables for sensitive configuration.

No database passwords, API keys, email passwords, or JWT secrets should be stored
directly in the source code.

### Environment Variables

```text
MONGODB_URI
REDIS_URL
MAIL_PORT
MAIL_USERNAME
MAIL_PASSWORD
WEATHER_API_KEY
JWT_SECRET
KAFKA_SERVERS
```

### Example

The application configuration uses environment variable placeholders:

```yaml
spring:
  data:
    mongodb:
      uri: ${MONGODB_URI}

    redis:
      url: ${REDIS_URL}

  mail:
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}

weather:
  api:
    key: ${WEATHER_API_KEY}

jwt:
  secret: ${JWT_SECRET}
```

Set these variables in your local environment before running the application.

> Never commit actual credentials, API keys, passwords, or JWT secrets to GitHub.

---

## 📋 Prerequisites

Make sure the following are installed and configured:

- Java JDK
- MongoDB
- Redis
- Apache Kafka

Additional configuration is required for:

- Email service
- Weather API

---

## ▶️ Running the Application

### 1. Clone the repository

```bash
git clone https://github.com/ShshankSingla/journalApp.git
```

### 2. Navigate to the project

```bash
cd journalApp
```

### 3. Configure environment variables

Set the required environment variables mentioned in the Configuration section.

### 4. Run the application

Using Maven Wrapper on Windows:

```bash
mvnw.cmd spring-boot:run
```

Or using Maven:

```bash
mvn spring-boot:run
```

---

## 🧪 Running Tests

Run the test suite using:

```bash
mvnw.cmd test
```

Or:

```bash
mvn test
```

The project contains both unit tests and Spring Boot integration tests.

Some integration tests require external services such as MongoDB, Redis,
Kafka, and email configuration.

---

## 🔐 Authentication & Security

The application uses Spring Security with JWT-based authentication.

Security features include:

- JWT authentication
- Password hashing
- Role-based authorization
- Protected API endpoints
- Environment-based secret configuration

---

## 🗄️ MongoDB

MongoDB is used as the primary database for storing:

- Users
- Journal entries
- Application configuration

The MongoDB connection is configured using:

```text
MONGODB_URI
```

---

## ⚡ Redis

Redis is used for application caching and other Redis-based operations.

Redis connection details are configured using:

```text
REDIS_URL
```

---

## 📨 Apache Kafka

Apache Kafka is used for asynchronous processing and sentiment-related functionality.

Kafka server configuration is provided using:

```text
KAFKA_SERVERS
```

For a local Kafka installation, the default value can be:

```text
localhost:9092
```

---

## 🌤️ Weather API

The application integrates with a weather API to retrieve weather information.

The API key is configured using:

```text
WEATHER_API_KEY
```

---

## 📧 Email Service

Spring Mail is used for sending emails.

The following environment variables are required:

```text
MAIL_PORT
MAIL_USERNAME
MAIL_PASSWORD
```

---

## 🧪 Testing

The project includes tests for:

- Spring Boot application context
- User service
- User repository
- User details service
- Redis operations
- Email service
- Scheduled user operations

Mockito is used for unit testing where external dependencies can be mocked.

---

## 🔮 Future Improvements

- Add Swagger/OpenAPI documentation
- Improve test coverage
- Improve exception handling
- Add Docker support
- Add CI/CD pipeline
- Improve integration testing
- Add API documentation
- Add frontend application

---

## 👨‍💻 Author

**Shshank Singla**

GitHub: https://github.com/ShshankSingla