
# Spring Boot Project

## Table of Contents
- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Contact](#contact)

## Overview
This document provides step-by-step instructions on how to set up, configure, and run the Spring Boot project. The project includes features such as authentication, role-based access control, and integration with a PostgreSQL database.

## Prerequisites
Before you start, ensure you have the following installed:
- Java Development Kit (JDK) 17 or higher
- Maven 3.6.3 or higher
- PostgreSQL 13 or higher
- Git

## Installation

### Clone the Repository
```sh
git clone git@git.enigmacamp.com:enigma-camp/oflline-class-lovelace/lovelace-17/hadiat-abdul-bashit/be/livecode/live-code-5.git
idea live-code-5
```

### Build the Project
Use Maven to build the project:
```sh
mvn clean install
```

## Configuration

### Database Configuration
Create a PostgreSQL database and update the `application.properties` file with your database credentials.

```properties
# src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/{yourdatabase}
spring.datasource.username={yourusername}
spring.datasource.password={yourpassword}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### JWT Configuration
Update JWT configuration properties in `application.properties`:
```properties
application.security.jwt.secret-key={your_jwt_secret_key}
application.security.jwt.expiration=3600000 // One Hours
application.security.jwt.refresh-token.expiration=604800000 // One Week
```

## Running the Application

### Using Maven
You can run the Spring Boot application using Maven:
```sh
mvn spring-boot:run
```

### Using Executable JAR
Alternatively, you can run the executable JAR file generated in the `target` directory:
```sh
java -jar target/live-code-5-0.0.1-SNAPSHOT.jar
```

### Accessing the Application
The application should now be running on `http://localhost:8080`.


## Contact
For further assistance, please contact [hadiatab16@gmail.com](mailto:hadiatab16@email.com).
