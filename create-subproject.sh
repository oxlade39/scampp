#!/bin/sh

if [ $# -eq 0 ]
then
echo "$0 : specify an artifactId"
exit 1
fi

mvn archetype:create -DartifactId=$1 -DgroupId=org.comperio