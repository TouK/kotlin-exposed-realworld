FROM openjdk:8-jre

ENV SERVER_PORT 8080
EXPOSE 8080

COPY build/libs/kotlin-spring-realworld.jar /

ADD docker/wait-for-it.sh /tools/wait-for-it.sh
RUN chmod +x /tools/wait-for-it.sh

ADD docker/run-app.sh /app/run-app.sh
RUN chmod +x /app/run-app.sh

WORKDIR /app

CMD ["./run-app.sh"]
