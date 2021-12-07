FROM openjdk:17-slim-buster as builder

ARG GRADLE_OPTS

WORKDIR /app

COPY build.gradle.kts gradle.properties settings.gradle.kts gradlew ./

COPY gradle ./gradle

RUN ./gradlew dependencies --refresh-dependencies

COPY src ./src

RUN ./gradlew shadowJar


FROM gcr.io/distroless/java17-debian11:nonroot

EXPOSE 8080

WORKDIR /app

COPY --from=builder /app/build/libs/mcr-api-server-*-all.jar mcr-api-server.jar

CMD [ "mcr-api-server.jar" ]
