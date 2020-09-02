#!/bin/sh

cd api-gateway; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
cd auth-server; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
cd config-server; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
cd webservice-registry; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
cd movie-service; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
cd zipkin-server; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
