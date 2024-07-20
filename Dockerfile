# Use a base image with Java 21
FROM openjdk:21-ea-17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/club.jar /app/club.jar

# Specify the entry point with --enable-preview flag
ENTRYPOINT ["java", "--enable-preview", "-jar", "/app/club.jar"]
