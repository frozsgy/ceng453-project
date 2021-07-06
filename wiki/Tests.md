# Server
For the testing of the project, we have developed unit tests and also provided a Postman collection. Further details about them can be found below.

## Unit and Integration Tests

The unit and integration tests for the project are available under the `test` folder. The tests can be run through either an IDE, or during `mvn package`. 

* **GameControllerTests**: Tests of the GameController.
* **PlayerControllerTests**: Tests of the PlayerController.
* **GameServiceTests**: Tests of the GameService.
* **MatchServiceTests**: Tests of the MatchService.
* **PlayerServiceTests**: Tests of the PlayerService.


## Postman Collection 

To test the external API endpoints, we provide a Postman Collection, which is available at [server/docs/CENG453_Server_API_Endpoints.postman_collection.json](http://144.122.71.168:8080/ozan.alpay/group10/src/bf23fb162f2ed870c29c060d9de00af7db039269/server/docs/CENG453_Server_API_Endpoints.postman_collection.json). While testing the endpoints, you need to update the `Bearer` token using the response of the `Login` endpoint if the endpoint needs authentication. Setting the token project-wise is enough for all endpoints.

# Client

For the testing of the project, we have developed unit and GUI tests. Further details about them can be found below.

## Unit and GUI Tests

The unit and GUI tests for the project are available under the `test` folder. The tests can be run through either an IDE, or during `mvn package`. 

* **GameLogicTests**: Tests of the Game Logic.
* **LevelOneStrategyTests**: Tests of the Level One Strategy.
* **LevelTwoStrategyTests**: Tests of the Level Two Strategy.
* **LevelThreeStrategyTests**: Tests of the Level Three Strategy.
* **GameControllerTests**: Tests of the Game Screen GUI.
* **ForgotControllerTests**: Tests of the Reset Password Screen GUI.
* **HomeControllerTests**: Tests of the Home Screen GUI.
* **LoginControllerTests**: Tests of the Login Screen GUI.
* **RegisterControllerTests**: Tests of the Register Screen GUI.
