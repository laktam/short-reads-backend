# Short Reads Backend

## Overview

Short Reads is a backend application designed to facilitate the management and sharing of short-form content, including posts and user profiles. This application supports user authentication, profile management, and social interactions like following other users.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Docker Configuration](#docker-configuration)
- [CI/CD Pipeline](#ci/cd-pipeline)
    - [GitHub Webhook Integration](#gitHub-webhook-integration)
    - [Ngrok](#ngrok)
    - [Pipeline diagram](#pipeline-diagram)

## Features

- User registration and authentication
- Profile management (uploading profile pictures, updating user information)
- Creating, retrieving, and managing posts
- Following and unfollowing other users
- Pagination for posts
- Swagger documentation for the API

## Technologies Used

- **Java**: 17
- **Spring Boot**: 3.3.5
- **Spring Security**: For authentication and authorization
- **Spring Data JPA**: For database interactions
- **MySQL**: As the database
- **Swagger/OpenAPI**: For API documentation
- **Maven**: For project management and build
- **Docker**
- **Jenkins**

## Docker Configuration
The application uses multiple Docker containers:

1. Backend Application Container
- Base image: openjdk:21-jdk-slim
- Exposed port: 8080
- Contains the Spring Boot application

2. MySQL Database Container
- Image: grugnog/mysql-5.1
- Exposed port: 3306
- Initialized with [sql-script](./other/short-read.sql)

Here is the database schema : 

![Data base schema](./other/ShortReads.png)

## CI/CD Pipeline
The project uses Jenkins for continuous integration and deployment with the following stages:

- Checkout: Clones the repository
- Create Network: Sets up Docker network
- Start MySQL: Deploys MySQL container
- Build: Compiles and packages the application
- Build Docker Image: Creates the application container image
- Deploy: Runs the application container

### GitHub Webhook Integration

The pipeline is triggered automatically through GitHub webhooks when changes are pushed to the repository.

### Ngrok
The project uses [Ngrok](https://ngrok.com/) as a secure tunneling solution to expose the local Jenkins server to the internet. This is crucial for the webhook functionality as GitHub needs a public URL to send webhook events.

Ngrok creates a secure tunnel that allows GitHub to communicate with Jenkins running locally:
- Generates a public URL
- Routes GitHub webhook requests to local Jenkins server
This enables GitHub webhooks to trigger our Jenkins pipeline without requiring a public IP

### Pipeline diagram
```mermaid
graph TD
   A[Start Pipeline] --> B[Checkout Repository]
   B[Checkout Repository<br/>Clone from GitHub] --> C[Network Setup]
   C[Network Setup<br/>Create Docker Network] --> D[Database Setup]
   D[Database Setup<br/>Start MySQL Container] --> E[Application Build]
   E[Application Build<br/>Maven Package] --> F[Docker Build]
   F[Docker Build<br/>Create Application Image] --> G[Application Deploy]
   G[Application Deploy<br/>Run Backend Container] --> H{Deployment Status}
   H -->|Success| I[Success Notification]
   H -->|Failure| J[Failure Notification]

   style A fill:#e6f3ff
   style B fill:#f0fff0
   style C fill:#fff0f0
   style D fill:#f0f0ff
   style E fill:#fff3e6
   style F fill:#f0ffe6
   style G fill:#ffe6f0
   style H fill:#f3f3f3
```