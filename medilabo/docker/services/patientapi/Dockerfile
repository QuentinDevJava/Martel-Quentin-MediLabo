FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=patientapi.jar
COPY ${JAR_FILE} patientapi.jar
ENTRYPOINT ["java", "-jar", "/patientapi.jar"]