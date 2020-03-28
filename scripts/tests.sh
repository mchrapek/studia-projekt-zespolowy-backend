#!/bin/bash

cd ../auth-service && ./test.sh
cd ../catalogue-service && ./test.sh
cd ../mail-service && ./test.sh
cd ../payment-service && ./test.sh
cd ../reservation-service && ./test.sh
cd ../user-service && ./test.sh
