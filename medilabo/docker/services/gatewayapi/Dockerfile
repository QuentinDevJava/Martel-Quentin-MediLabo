FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=gatewayapi.jar
COPY ${JAR_FILE} gatewayapi.jar
ENTRYPOINT ["java", "-jar", "/gatewayapi.jar"]