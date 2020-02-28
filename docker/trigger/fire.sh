#!/usr/bin/env sh

while true
do
  STATUS=$(curl -s -o /dev/null -w '%{http_code}' http://jenkins:8080/job/java-tomcat-maven-example/buildWithParameters --data-urlencode json='{"parameter": [{"DOCKER_TAG":"joaquinvargascr/reto-devops-jenkins"}]}')
  if [ $STATUS -eq 201 ]; then
    echo "Got 201! All done!"
    break
  else
    echo "Got $STATUS :( Not done yet..."
  fi
  sleep 10
done