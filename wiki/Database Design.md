While developing the project, we decided to use an ORM solution to create and persist database structure. In order to achieve this, we have created entities that are representations of the tables that we would need. Since each table would need a primary key (id), activation status, creation date and update date, we opted for creating a base entity and extending all entities from that. The Foreign Key relations are achieved through JPA annotations such as `OneToMany`, `ManyToMany`. 

## Schemas

* **player**: Stores all login information for the players.
* **role**: Stores different role types such as `USER` and `ADMIN`.
* **player_roles**: Stores the relationship between player and role tables.
* **pending_pw_code**: Stores password reset tokens.
* **game**: Stores general information about the game. A game consists of 4 rounds.
* **rounds**: Stores round information. 

The ER diagram of the project can be found below.

![ER Diagram](http://144.122.71.168:8080/ozan.alpay/group10/raw/2964788653a87c459e91f51a56a3f59ad0ed5cc6/server/img/er.jpg)