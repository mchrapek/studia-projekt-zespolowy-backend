#!/bin/bash

mvn clean test -f ../common/pom.xml
mvn clean test -f ../auth-service/pom.xml
mvn clean test -f ../catalogue-service/pom.xml
mvn clean test -f ../eureka-service/pom.xml
mvn clean test -f ../gateway-service/pom.xml
mvn clean test -f ../mail-service/pom.xml
mvn clean test -f ../user-service/pom.xml
