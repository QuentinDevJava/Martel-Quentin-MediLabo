FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
RUN apk add --no-cache curl

WORKDIR /app
COPY /target/gatewayapi.jar .

ENTRYPOINT ["java", "-jar", "gatewayapi.jar"]