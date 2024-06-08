# Stage 1: Build the application
FROM maven:3.8.1-openjdk-17-slim AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean install -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar ./app.jar

# Expose the port the application will run on
EXPOSE 8091

# Define the command to run the application
CMD ["java", "-jar", "app.jar"]
