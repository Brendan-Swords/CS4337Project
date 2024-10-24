FROM openjdk:21-jdk-slim

WORKDIR /app

# Adjust the context in your docker-compose.yml
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
