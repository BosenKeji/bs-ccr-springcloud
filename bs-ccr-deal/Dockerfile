FROM registry.us-east-1.aliyuncs.com/bs-ccr/spring-cloud-base:latest

COPY . .

RUN  mvn package -nsu -Dmaven.test.skip=true

#VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 9100
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]