# Requirements

* JDK 16
* Maven
* Apache Tomcat

# Instructions

## Server


1. Use `mvn package` to package the project into a war file. 
2. Deploy the created `server10.war` file into your Apache Tomcat server.
3. The server should be up and running at `http://localhost:8083/server10/`.

## Client

1. Use `mvn package` to package the project into a jar file. 
2. Run the created jar file in the target folder as follows: `java -jar client10.jar`
3. Run the backend server according to the instructions in the server folder.
4. The application should be up and running.