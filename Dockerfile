FROM eclipse-temurin:21-jre

WORKDIR application
ARG JAR_FILE=starter/build/libs/portal-server*.jar
COPY ${JAR_FILE} /application/application.jar
COPY starter/src/main/resources/application.yml /application/config/application.yml
COPY starter/src/main/resources/docker/.env /application/config/.env

ENV JVM_OPTS="-Xmx512m -Xms2048m" \
    APP_OPS="--spring.config.import=optional:file:/application/config/.env[.properties]" \
    TZ=Asia/Shanghai

ENTRYPOINT [ "java", "${JVM_OPTS}", "-jar", "/application/application.jar", "${APP_OPS}" ]

EXPOSE 8080
