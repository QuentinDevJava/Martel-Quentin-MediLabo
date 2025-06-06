FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=noteapi.jar
COPY ${JAR_FILE} noteapi.jar
ENV WAIT_VERSION 2.7.2
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/${WAIT_VERSION}/wait /wait
RUN chmod +x /wait

CMD ["/wait", "--", "java", "-jar", "noteapi.jar"]