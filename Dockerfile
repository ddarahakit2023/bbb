FROM openjdk:11-jdk-slim-stretch
COPY ./target/api-0.0.1-SNAPSHOT.jar app.jar
CMD ["java","-jar","app.jar"]