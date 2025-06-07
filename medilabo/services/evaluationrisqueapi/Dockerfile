FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
RUN apk add --no-cache curl

WORKDIR /app
COPY /target/evaluationrisqueapi.jar .

ENTRYPOINT ["java", "-jar", "evaluationrisqueapi.jar"]