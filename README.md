# HearMeOut Backend – Q&A Platform

HearMeOut is a StackOverflow-style Q&A backend built using Spring Boot and Spring Data JPA. It provides APIs for creating questions, posting answers, commenting, and voting, following a clean layered architecture.

This project demonstrates how to implement a production-style REST API with a structured architecture including controllers, services, repositories, DTOs, and global exception handling.

---

## Features

- Post and Comment management (create, read, update, delete)
- Tag management with auto-creation
- Vote tracking per user per post
- User management with reputation tracking
- Single-table design for Questions and Answers using a post_type discriminator
- DTO layer for clean API responses
- Global exception handling with consistent API response wrapper
- Data validation using Jakarta Bean Validation

---

## Tech Stack

- **Backend:** Spring Boot, Spring Data JPA
- **Language:** Java
- **Database:** MySQL/PostgreSQL (later)
- **Build Tool:** Maven
- **API Style:** RESTful APIs

---

## Project Structure

```text
HearMeOut_Backend/
├─ pom.xml
├─ mvnw
├─ mvnw.cmd
├─ .mvn/
├─ src/
│  ├─ main/
│  │  ├─ java/com/project/hearmeout_backend/
│  │  │  └─ service/
│  │  │  └─ controller/
│  │  │  └─ repository/
│  │  │  └─ model/
│  │  │  └─ dto/
│  │  │  └─ exception/
│  │  │  └─ HearMeOutBackendApplication.java
│  │  └─ resources/
│  └─ test/
└─ .gitignore
```

---

## Domain Model (ER Diagram)

```mermaid
erDiagram
  USER {
    bigint id
    string username
    string password_hashed
    int reputation
    string email
    datetime created_at
    boolean is_email_verified
    string email_verify_otp
    string password_change_otp
    datetime password_otp_expire_at
    datetime email_verify_otp_expire_at
    boolean is_account_verified
    boolean is_account_active
  }

  POST {
    bigint id
    string title
    string description
    bigint author_id
    datetime created_at
    string post_type  "question|answer"
    bigint parent_id
  }

  COMMENT {
    bigint id
    bigint author_id
    string content
    datetime created_at
    bigint post_id
  }

  VOTE {
    bigint id
    string vote_type "upvote|downvote"
    bigint post_id
    bigint user_id
    datetime created_at
  }

  TAG {
    bigint id
    string name
    string description
  }

  TAG_USED {
    bigint post_id
    bigint tag_id
  }

  USER ||--o{ POST : posted
  POST ||--o{ COMMENT : comment_on
  USER ||--o{ COMMENT : comment_by

  POST ||--o{ VOTE : voted_on
  USER ||--o{ VOTE : voted_by

  POST ||--o{ TAG_USED : uses
  TAG  ||--o{ TAG_USED : used_on

  POST ||--o{ POST : part_of
```

---

## Getting Started

### 1️. Clone the repository

```bash
git clone https://github.com/rarestpreet/HearMeOut_Backend.git
```

### 2️. Set environment variables

Configure the required environment variables:

(upcoming commits)
- `DB_URL`
- `DB_USER`
- `DB_PASS`
- `SECRET_KEY`
- `MAIL_USER`
- `MAIL_PASS`

### 3️. Run the application

Run the main class:

- `HearMeOutBackendApplication.java`

Server will start at:

- `http://localhost:8080`

---
