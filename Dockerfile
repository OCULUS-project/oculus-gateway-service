FROM openjdk:8-jdk-alpine

COPY build /app/build

WORKDIR /app

ENV spring_profiles_active dev
EXPOSE 8080

CMD java -jar build/libs/oculus-gateway-service-0.1.0.jar -Dspring.profiles.active=dev
