#FROM openjdk:8
#ADD build/libs/mccauthapi.jar mccauthapi.jar
#
#EXPOSE 8081
#
#ARG targethost=localhost:8080
#ENV API_HOST=$targethost
#
#ENTRYPOINT ["java", "-jar", "mccauthapi.jar"]


FROM gradle:jdk10 as builder
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle bootJar

FROM openjdk:8-jdk-alpine
EXPOSE 8081

ARG targethost=localhost:8080
ENV API_HOST=$targethost

VOLUME /tmp
ARG LIBS=app/build/libs
COPY --from=builder ${LIBS}/ /app/lib
ENTRYPOINT ["java","-jar","./app/lib/mccauthapi.jar"]
