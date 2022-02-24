FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/hanju-spider-1.0.0.jar hanju-spider-1.0.0.jar
ENTRYPOINT ["java","-jar","/hanju-spider-1.0.0.jar", "&"]