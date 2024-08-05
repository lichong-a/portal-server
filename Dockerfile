FROM eclipse-temurin:21-jre

WORKDIR application
ARG JAR_FILE=starter/build/libs/portal-server*.jar
COPY ${JAR_FILE} application.jar

ENV JVM_OPTS="-Xmx512m -Xms2048m" \
    TZ=Asia/Shanghai

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JVM_OPTS -jar application.jar" ]
