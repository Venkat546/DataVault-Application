# Stage 1: Build
FROM maven:3.9-eclipse-temurin-22 AS build

# Set the working directory
WORKDIR /app

# Copy the project files
COPY pom.xml /app/
COPY src /app/src/

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:23

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Expose the port
EXPOSE 8080

# Set the environment variables
ENV MYSQL_HOST=mysql
ENV MYSQL_PORT=3306
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=mysql@01
ENV MYSQL_DB=file_upload_db
ENV FILE_UPLOAD_DIR=/app/upload

# Set the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
