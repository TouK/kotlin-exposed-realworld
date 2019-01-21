FROM openjdk:8-jre

ENV SERVER_PORT 8080
EXPOSE 8080

COPY build/libs/kotlin-spring-realworld.jar /
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/kotlin-spring-realworld.jar"]
