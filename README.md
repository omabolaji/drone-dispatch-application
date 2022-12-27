# drone-dispatch-application
Drone dispatcher application for MusalaSoft Interview Test

Spring Boot Application
This is a Spring Boot application that does Drone dispatcher.

Prerequisites
Before you can run the application, you will need to have the following software installed on your system:

Java 11 or later
Maven 2.7.5 or later
Running the Application
To run the application, follow these steps:

Open a terminal window and navigate to the root directory of the project.
Run the following command to build the application:
Copy code
mvn clean package
Run the following command to start the application:
Copy code
java -jar target/drones-0.0.1-SNAPSHOT.jar
The application will start up on port 9000 by default. You can access it by opening a web browser and navigating to http://localhost:9000.

Database:
H2 in memory db was used and it has been preloaded with data on runtime for testing purpose

Configuration
The application can be configured using the following properties:

server.port: The port that the application will listen on.
my.property: A property that does X.
You can override these properties by creating a file called application.properties in the root directory of the project and setting the desired values.

For example, to change the port to 9000, you could add the following line to the application.properties file:

