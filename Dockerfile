FROM eclipse-temurin:21-jdk-alpine
LABEL authors="harshvardhanparmar"

# Set working directory
WORKDIR /app

# Copy the Spring Boot JAR into the container
COPY target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]