# Publishing service dockerfile
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY build/libs/Publish-0.0.1-SNAPSHOT.jar app.jar

# Port the service will use (application.properties, 8081)
EXPOSE 8081

# Entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]