#!/bin/bash

curl -XGET http://localhost:8762/actuator/health
curl -XGET http://localhost:9100/actuator/health
curl -XGET http://localhost:9105/actuator/health
curl -XGET http://localhost:9110/actuator/health
curl -XGET http://localhost:9115/actuator/health
curl -XGET http://localhost:9120/actuator/health
curl -XGET http://localhost:9125/actuator/health