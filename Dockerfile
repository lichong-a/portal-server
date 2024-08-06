FROM eclipse-temurin:21-jre

WORKDIR application
ARG JAR_FILE=starter/build/libs/portal-server*.jar
COPY ${JAR_FILE} application.jar
COPY starter/src/main/resources/application.yml application.yml
COPY starter/src/main/resources/docker/.env .env

ENV JVM_OPTS="-Xmx512m -Xms2048m" \
    TZ=Asia/Shanghai

EXPOSE 8080

ENTRYPOINT [ "java", "$JVM_OPTS", "-jar", "application.jar", "--spring.config.import=optional:file:.env[.properties]" ]
