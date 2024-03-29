# CENG453 Pişti Game - Backend Server 

This folder contains the backend server application for the CENG453 Project, Pişti. It was developed using Spring Boot, MariaDB and Apache Tomcat. 

# Getting Started

## Requirements

* JDK 16
* Maven
* Apache Tomcat

## Build and Run

1. Use `mvn package` to package the project into a war file. 
2. Deploy the created `server10.war` file into your Apache Tomcat server.
3. The server should be up and running at `http://localhost:8083/server10/`.

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

## Swagger 

The Swagger API Documentation can be accessed at [docs/swagger-api-docs.json](docs/swagger-api-docs.json). Also while the application is running, the API Documentation through Swagger can be accessed at the following URL: [http://144.122.71.168:8083/server10/swagger-ui.html](http://144.122.71.168:8083/server10/swagger-ui.html). 

## JavaDoc

The JavaDoc files are available under the [docs/javadocs](docs/javadocs) folder.

## Postman

The documentation generated using Postman is also available at [https://documenter.getpostman.com/view/12050224/TzRLkVr2](https://documenter.getpostman.com/view/12050224/TzRLkVr2).

 
# Tests

For the testing of the project, we have developed unit tests and also provided a Postman collection. Further details about them can be found below.

## Unit and Integration Tests

The unit and integration tests for the project are available under the `test` folder. The tests can be run through either an IDE, or during `mvn package`. 

* **GameControllerTests**: Tests of the GameController.
* **PlayerControllerTests**: Tests of the PlayerController.
* **GameServiceTests**: Tests of the GameService.
* **MatchServiceTests**: Tests of the MatchService.
* **PlayerServiceTests**: Tests of the PlayerService.


## Postman Collection 

To test the external API endpoints, we provide a Postman Collection, which is available at [docs/CENG453_Server_API_Endpoints.postman_collection.json](docs/CENG453_Server_API_Endpoints.postman_collection.json). While testing the endpoints, you need to update the `Bearer` token using the response of the `Login` endpoint if the endpoint needs authentication. Setting the token project-wise is enough for all endpoints.
