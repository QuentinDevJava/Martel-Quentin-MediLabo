FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=evaluationrisqueapi.jar
COPY ${JAR_FILE} evaluationrisqueapi.jar
ENTRYPOINT ["java", "-jar", "/evaluationrisqueapi.jar"]