#LABEL authors="darshanpahilwani"


FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file to the container
COPY target/Health-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port on which the Spring Boot app will run
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

