FROM eclipse-temurin:21-jdk-alpine

COPY build/libs/poc-appconfig-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]