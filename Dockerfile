FROM openjdk:17-jdk-slim

LABEL authors="alexs"

WORKDIR /app

COPY target/FoodTracker-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
