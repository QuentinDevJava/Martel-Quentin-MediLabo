FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=eurekaserver.jar
COPY ${JAR_FILE} eurekaserver.jar

ENTRYPOINT ["java", "-jar", "eurekaserver.jar"]