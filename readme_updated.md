# Elevator Coding Challenge
Created elevator simulation based on guava based EventBus
##Solution Description
1) Created ElevatorEvent that stores request information i.e. elevator id and floor number requested.
2) Implemented ElevatorEventListener that subscribes to Elevator Events and handles them.
Eventbus allows publish-subscribe communication and submit tasks to the elevator pool. 
ElevatorEventListener subscribes to elevator events.
3) Implemented Elevator interface in SimpleElevator
4) Implemented ElevatorController in ElevatorService.
5) Implemented REST endpoints
5) Added unit tests under /test/java.
6) Implemented IntegrationTest which posts 50 incoming elevator event requests with random elevator and floor numbers simulating real world scenario.

##How to build and run the application
mvn clean install
mvn spring-boot:run

Number of elevators and floors can be adjusted in application.properties.

Monitoring of the application can be done through logs as logging level is set to info.