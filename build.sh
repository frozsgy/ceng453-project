#!/bin/bash
rm -fr executables
mkdir executables
echo "Creating group10.war..."
cd server
mvn clean package
cd target
cp group10.war ../../executables/
echo "Creating group10.jar..."
cd ../../client
mvn clean package
cd target
cp group10.jar ../../executables/

