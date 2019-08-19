FROM openjdk:8-jdk
VOLUME /tmp
ADD target/${JAR_FILE} app.jar
EXPOSE 9100
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]