FROM openjdk:8-slim

COPY build /app/build

WORKDIR /app

ENV spring_profiles_active dev
EXPOSE 8080

CMD java -jar build/libs/oculus-gateway-service-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=dev