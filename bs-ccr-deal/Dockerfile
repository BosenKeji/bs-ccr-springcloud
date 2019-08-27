FROM openjdk:8-jdk

RUN mkdir /opt/app
COPY . /opt/app
WORKDIR /opt/app

RUN ./mvnw clean

#VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 9100
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]