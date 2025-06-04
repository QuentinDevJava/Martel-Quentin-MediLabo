FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=configserver.jar
COPY ${JAR_FILE} configserver.jar
ENTRYPOINT ["java", "-jar", "/configserver.jar"]