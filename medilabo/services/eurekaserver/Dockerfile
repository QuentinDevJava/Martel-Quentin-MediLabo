FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
RUN apk add --no-cache curl

WORKDIR /app
COPY /target/eurekaserver.jar .

ENTRYPOINT ["java", "-jar", "eurekaserver.jar"]