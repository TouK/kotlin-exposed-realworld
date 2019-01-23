FROM node:11-alpine

RUN apk add --update netcat-openbsd bash && rm -rf /var/cache/apk/*

ADD acceptance /acceptance
ADD ./docker/wait-for-it.sh /tools/wait-for-it.sh
RUN chmod +x /tools/wait-for-it.sh

VOLUME /acceptance/newman

WORKDIR /acceptance

CMD ["./run-api-tests.sh"]
