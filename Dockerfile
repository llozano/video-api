FROM openjdk:23-jdk-slim
WORKDIR /app


COPY build/libs/videoapi-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]