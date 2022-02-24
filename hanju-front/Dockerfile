FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/front-1.0.0.jar front-1.0.0.jar
ENTRYPOINT ["java","-jar","/front-1.0.0.jar", "&"]