FROM gradle:5.4.1-jdk8-alpine

COPY --chown=gradle:gradle . /app

WORKDIR /app
RUN gradle build

EXPOSE 8080
CMD java -jar build/libs/oculus-gateway-service-0.0.1-SNAPSHOT.jar