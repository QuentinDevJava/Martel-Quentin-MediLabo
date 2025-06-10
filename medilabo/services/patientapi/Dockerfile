FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
RUN apk add --no-cache curl

ENV SPRING_PROFILES_ACTIVE=docker

WORKDIR /app
COPY target/patientapi.jar .

ENTRYPOINT ["java", "-jar", "patientapi.jar"]