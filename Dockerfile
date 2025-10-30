# === STAGE 1: BUILDER ===
# Use the Maven image to compile and package the application
FROM maven:3.9.5-eclipse-temurin-21-alpine AS builder

# Set the working directory
WORKDIR /app

# Copy project files (pom.xml first for caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build the JAR
COPY src ./src
# The command below creates the JAR file in /app/target/
RUN mvn clean package -DskipTests

# === STAGE 2: RUNNER ===
# Use a minimal JRE image for the final production deployment
FROM eclipse-temurin:21-jre-alpine

# Set the working directory for the running app
WORKDIR /app

# Expose the standard web port (Render uses the PORT env var)
EXPOSE 8080

# CRITICAL FIX: Copy the built JAR from the 'builder' stage
# The wildcard (*) ensures you don't break if the version number changes
COPY --from=builder /app/target/*.jar app.jar

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
