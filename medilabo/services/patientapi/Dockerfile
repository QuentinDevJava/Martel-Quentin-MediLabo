FROM openjdk:22-slim-bullseye

WORKDIR /app

COPY target/*.jar /app/patientapi.jar

ENTRYPOINT ["java", "-jar", "/app/patientapi.jar"]