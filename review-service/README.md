#Overview

This application provides the **reviews and ratings** related functionality and serves as one component. It defines the REST endpoints that are used to provide review functionality.

This micro-service also provides an example of to call another OAuth2 protected service from within this service using OAuth2 client configuration. The OAuth2 bearer token that has been passed to the review service is propagated to the "movie" service to get the list of movies.

##Pre-requisites

### Projects that need to be started before
* [config server](/../../blob/master/config-server/README.md) - For pulling the configuration information
* [webserver-registry](/../../blob/master/webservice-registry/README.md) - For starting the Eureka server since the authorization server also is a micro-service that needs to be registered with Eureka server.    

### Running the application
* Build the application by running the `./gradlew clean build` gradle command at the "review-service" project root folder	on the terminal.
* If you want to run the application as jar file, then run `java -jar build/libs/review-service-0.0.1.jar` command at the terminal.

 ```
 ./gradlew clean build
 ```