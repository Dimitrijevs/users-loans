FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/eureka-server-1.0.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]