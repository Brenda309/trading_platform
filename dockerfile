FROM ubuntu:latest
LABEL authors="brendawihogora"

ENTRYPOINT ["top", "-b"]

# Use an official OpenJDK base image
FROM openjdk:17-jdk-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml to the container
COPY .mvn .mvn
COPY mvnw pom.xml ./

# Download and cache dependencies (useful for speeding up future builds)
RUN ./mvnw dependency:go-offline

# Copy the entire source code to the container
COPY src ./src

# Package the application (using Maven)
RUN ./mvnw clean package -DskipTests

# Use a smaller image for running the app (distroless or openjdk image)
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=build /app/target/trading-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app will run on (default Spring Boot port is 8080)
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]