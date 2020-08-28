FROM openjdk:8
ADD build/libs/mccauthapi.jar mccauthapi.jar

EXPOSE 8081

ARG targethost=localhost:8080
ENV API_HOST=$targethost

ENTRYPOINT ["java", "-jar", "mccauthapi.jar"]
