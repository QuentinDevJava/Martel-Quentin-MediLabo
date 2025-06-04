FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=noteapi.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]