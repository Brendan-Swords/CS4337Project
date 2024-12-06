FROM eclipse-temurin:17-jdk-alpine
LABEL authors="Rory"

COPY build/libs/ApiGateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
