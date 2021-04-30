# CENG453 Pişti Game - Backend Server 

This folder contains the backend server application for the CENG453 Project, Pişti. It was developed using Spring Boot, MariaDB and Apache Tomcat. 

# Getting Started

## Requirements

* JDK 16
* Maven

## Build and Run

1. Use `mvn package` to package the project into a jar file. 
2. Run the created jar file in the target folder as follows: `java -jar server-0.0.1-SNAPSHOT.jar`
3. The server should be up and running at `http://localhost:8080/`.

# Database Design 

While developing the project, we decided to use an ORM solution to create and persist database structure. In order to achieve this, we have created entities that are representations of the tables that we would need. Since each table would need a primary key (id), activation status, creation date and update date, we opted for creating a base entity and extending all entities from that. The Foreign Key relations are achieved through JPA annotations such as `OneToMany`, `ManyToMany`. 

## Schemas

* **player**: Stores all login information for the players.
* **role**: Stores different role types such as `USER` and `ADMIN`.
* **player_roles**: Stores the relationship between player and role tables.
* **pending_pw_code**: Stores password reset tokens.
* **game**: Stores general information about the game. A game consists of 4 rounds.
* **rounds**: Stores round information. 

The ER diagram of the project can be found below.

![ER Diagram](img/er.jpg)


# API Documentation

The API Documentation through Swagger can be accessed while the application is running at the following URL: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

 
# Tests

For the testing of the project, we have developed unit tests and also provided a Postman collection. Further details about them can be found below.

## Unit Tests

-- TODO 

## Postman Collection 

To test the external API endpoints, we provide a Postman Collection, which is available at [docs/CENG453_Server_API_Endpoints.postman_collection.json](docs/CENG453_Server_API_Endpoints.postman_collection.json). Also, the documentation of the Postman Collection is available at [https://documenter.getpostman.com/view/12050224/TzRLkVr2](https://documenter.getpostman.com/view/12050224/TzRLkVr2).