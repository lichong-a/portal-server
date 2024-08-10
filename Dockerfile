FROM eclipse-temurin:21-jre

WORKDIR application
ARG JAR_FILE=starter/build/libs/portal-server*.jar
COPY ${JAR_FILE} /application/
COPY starter/src/main/resources/application.yml /application/config/application.yml
COPY starter/src/main/resources/docker/.env /application/config/.env

ENV JVM_OPTS="-Xmx2048m -Xms512m" \
    APP_OPS="--spring.config.import=optional:file:/application/config/.env[.properties]" \
    TZ=Asia/Shanghai

ENTRYPOINT [ "sh", "-c", "java ${JVM_OPTS} -jar /application/portal-server*.jar ${APP_OPS}" ]

EXPOSE 8080
