#!/bin/bash

REPOSITORY_ID=$1
REPOSITORY_URL=$2
mvn -DaltDeploymentRepository=$REPOSITORY_ID::default::$REPOSITORY_URL clean deploy