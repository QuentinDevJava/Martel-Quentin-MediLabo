FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=ui.jar
COPY ${JAR_FILE} ui.jar

ENTRYPOINT ["java", "-jar", "ui.jar"]