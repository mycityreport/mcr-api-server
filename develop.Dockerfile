FROM openjdk:17-slim-buster

WORKDIR /app

COPY build.gradle.kts gradle.properties settings.gradle.kts gradlew ./

COPY gradle ./gradle

COPY src ./src

RUN ./gradlew installDist
