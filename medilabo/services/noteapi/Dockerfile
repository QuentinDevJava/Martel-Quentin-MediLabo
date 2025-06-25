# ---- Stage 1: Build the application ----
FROM maven:3.9-eclipse-temurin-22 AS builder
WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ---- Stage 2: Run the application ----
FROM openjdk:22

WORKDIR /app
COPY --from=builder /build/target/*.jar noteapi.jar
EXPOSE 5002
ENTRYPOINT ["java", "-jar", "/app/noteapi.jar"]