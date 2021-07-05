#!/bin/bash
rm -fr executables
mkdir executables
echo "Creating server10.war..."
cd server
mvn clean install package
cd target
cp server10.war ../../executables/
echo "Creating client10.jar..."
cd ../../client
mvn clean install package
cd target
cp client10.jar ../../executables/

