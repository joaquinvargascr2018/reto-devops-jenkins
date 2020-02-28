FROM openjdk:8-jre-alpine

EXPOSE 8080

ADD  target /target

CMD /usr/bin/java -jar /target/dependency/webapp-runner.jar /target/java-tomcat-maven-example.war


