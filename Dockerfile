FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install 

RUN apt-get install -y mongodb-org
RUN systemctl start mongod


FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/enxolist-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]