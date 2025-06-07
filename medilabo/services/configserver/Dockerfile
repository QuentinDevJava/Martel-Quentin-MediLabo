FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
RUN apk add --no-cache curl

WORKDIR /app
COPY /target/configserver.jar .

ENTRYPOINT ["java", "-jar", "configserver.jar"]