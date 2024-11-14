## CS4337 Bookstore Project - Group 6

### Overview

Our project is a web application for an online bookstore, developed using Agile methodologies and a microservices architecture. It enables users to publish, view and purchase books, while supporting CRUD operations and secure user authentication.

### Key Technologies

	•	Backend: Spring Boot (Java)
	•	Database: MySQL 
	•	API Communication: REST
	•	CI/CD: GitHub Actions
	•	Testing: Postman, JUnit

### Architecture

Our application is organised into three microservices:
1.	Authentication Service - Manages user accounts.
2.  Publishing Service - Publishes and removes books.
3.	Order Service - Processes orders.

These services interact via REST APIs and are connected through an API Gateway.

### Database Schema

The schema includes tables for Users, Books, and Orders. Relationships are designed for efficient data access and fault tolerance.

### CI/CD Pipeline

Our GitHub Actions pipeline automates:
* Builds and tests (unit and integration)
* Dependency Management (submission of dependency graph)
* Deployment to a staging environment (Google Cloud)

### Setup

	1.	Clone Repository:
                git clone https://github.com/Brendan-Swords/CS4337Project.git
                cd CS4337Project

    2.	Run with Docker Compose:
                docker-compose up --build

    3.	Run Individually with Gradle - Navigate to each service folder and run:
                ./gradlew bootRun