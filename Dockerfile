# #LABEL authors="darshanpahilwani"


# FROM openjdk:21-jdk-slim

# # Set the working directory inside the container
# WORKDIR /app

# RUN mvn clean package -DskipTests

# # Copy the JAR file to the container
# COPY target/Health-0.0.1-SNAPSHOT.jar /app/app.jar

# # Expose the port on which the Spring Boot app will run
# EXPOSE 8083


# # Run the Spring Boot application
# ENTRYPOINT ["java", "-jar", "/app/app.jar"]


# Stage 1: Build the JAR using Maven
# Stage 1: Build the JAR using Maven
# Stage 1: Build the JAR using Maven (Java 17)
FROM maven:3.9.9-ibm-semeru-23-jammy AS builder

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create a lean image with just the JAR (Java 17)
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/Health-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/app.jar"]



