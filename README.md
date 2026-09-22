# InternHaul 

### Intern Project Allocation Portal

InternHaul is a Spring Boot-based project allocation portal designed to help organizations efficiently allocate interns to projects based on their skills, availability, and project requirements.

The idea is inspired by a common enterprise problem where interns may remain unallocated or on the bench while teams have projects requiring specific skills.

## 🎯 Problem

In large organizations, managing interns across multiple teams and projects can become difficult.

Some common challenges include:

* Interns remaining unallocated for long periods
* Difficulty finding interns with the required skills
* Manual allocation through spreadsheets or emails
* Lack of visibility into intern availability
* Difficulty tracking project requirements and allocations

InternHaul aims to provide a centralized system for managing this process.

## 💡 Solution

InternHaul provides a portal where administrators can:

* Manage intern profiles
* Manage projects
* Define required skills for projects
* Track intern availability
* Match interns with suitable projects
* Allocate interns to projects
* Monitor allocation information through a dashboard

The system is designed with scalability in mind so that it can be extended for larger organizational use cases.

## ✨ Features

### 🔐 Authentication & Authorization

* Secure login
* Role-based access
* Protected application pages

### 👨‍💻 Intern Management

* Add interns
* View intern details
* Manage skills
* Track availability

### 📋 Project Management

* Create projects
* Define project requirements
* Specify required skills
* Track project status

### 🔄 Project Allocation

* Match interns with projects
* Consider skills and availability
* Allocate interns to suitable projects
* Track existing allocations

### 📊 Dashboard

* Overview of interns
* Project information
* Allocation statistics
* Allocation-related insights

### ⚙️ Settings

* Application configuration
* User-related settings

## 🏗️ Architecture

InternHaul follows a layered Spring Boot architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

The application separates responsibilities across different layers to keep the code maintainable and easier to extend.

## 🛠️ Tech Stack

### Backend

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* Spring Security
* Hibernate

### Database

* MySQL

### Frontend

* Thymeleaf
* HTML
* CSS
* JavaScript

### Tools

* Maven
* Git
* GitHub
* IntelliJ IDEA / VS Code

## 📁 Project Structure

```text
src
└── main
    ├── java
    │   └── com.internhaul
    │       ├── controller
    │       ├── service
    │       ├── repository
    │       ├── entity
    │       ├── dto
    │       ├── config
    │       └── security
    │
    └── resources
        ├── templates
        ├── static
        └── application.properties
```

## 🚀 Getting Started

### Prerequisites

Make sure you have installed:

* Java 21+
* Maven
* MySQL
* Git

### 1. Clone the repository

```bash
git clone https://github.com/khushnumaparween/internhaul.git
```

### 2. Navigate to the project

```bash
cd internhaul
```

### 3. Configure the database

Create a MySQL database:

```sql
CREATE DATABASE internhaul;
```

Update your database configuration in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/internhaul
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

Do not commit real database credentials to GitHub.

### 4. Run the application

Using Maven:

```bash
mvn spring-boot:run
```

Or run the main Spring Boot application class from your IDE.

The application will be available at:

```text
http://localhost:8081
```

## 🔮 Future Enhancements

* Advanced skill-based matching
* Better allocation algorithms
* Manager/team-specific dashboards
* Email notifications
* Allocation history
* Search and filtering
* Pagination for large datasets
* REST API support
* Docker deployment
* Kubernetes support
* Redis caching
* Kafka-based event processing
* Microservice architecture for larger-scale deployment

## 📌 Why InternHaul?

InternHaul was built as a practical solution to an enterprise-style problem rather than as a simple CRUD demonstration.

The project focuses on how an internal organizational system could manage a larger pool of interns, projects, skills, and allocations while providing visibility to administrators and teams.

## 👩‍💻 Author

**Khushnuma Parween**

Java | Spring Boot | Backend Development

