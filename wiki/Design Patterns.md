During the development of the project, we have applied several design patterns to improve our project. You may find detailed explanation about them below.

## Strategy Pattern

Strategy Pattern was used while implementing the AI Strategies. Since each level has a different difficulty, creating a common interface for the AI and then implementing different strategies seemed the most suitable one for our project. A UML diagram of the mentioned structure can be seen below:

![Strategy Pattern UML Diagram](http://144.122.71.168:8080/ozan.alpay/group10/raw/master/client/docs/img/strategy.png)

## Singleton Pattern

We made use of the Singleton Pattern in our project on several occasions.
* Keeping state: Using singleton pattern allowed us to keep state between different user interfaces without reloading / fetching the data from the server.
* Keeping only one instance: Singleton pattern allowed us to keep some entities with only one instance, which was useful for some service classes (such as HTTPService)
* Minimizing overhead: Keeping only one copy of a class helped us with not having excess overhead due to creating and destroying the same objects over again and again.
