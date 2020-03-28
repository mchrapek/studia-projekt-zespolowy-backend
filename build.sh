#!/bin/bash

mvn clean install -f common/pom.xml -DskipTests
mvn clean install -f auth-service/pom.xml -DskipTests
mvn clean install -f catalogue-service/pom.xml -DskipTests
mvn clean install -f eureka-service/pom.xml -DskipTests
mvn clean install -f gateway-service/pom.xml -DskipTests
mvn clean install -f mail-service/pom.xml -DskipTests
mvn clean install -f user-service/pom.xml -DskipTests
mvn clean install -f reservation-service/pom.xml -DskipTests
mvn clean install -f payment-service/pom.xml -DskipTests
