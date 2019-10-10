FROM openjdk:8-jdk-alpine
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
COPY ./target/*.jar /app.jar
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar $JAVA_OPTS /app.jar