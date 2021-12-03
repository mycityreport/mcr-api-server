# syntax = docker/dockerfile:experimental

FROM openjdk:17-slim-buster as builder

WORKDIR /app

COPY build.gradle.kts gradle.properties settings.gradle.kts gradlew ./

COPY gradle ./gradle

COPY src ./src

RUN --mount=type=cache,target=/root/.gradle/caches \
    --mount=type=cache,target=/root/.m2/repository \
    --mount=type=cache,target=/app/.gradle \
    ./gradlew --no-daemon installDist


FROM openjdk:17-slim-buster

EXPOSE 8080

RUN mkdir /app
RUN useradd mcr
RUN chown mcr:mcr /app

COPY --from=builder --chown=mcr:mcr /app/build/install/mcr-api-server/ /app/

USER mcr

WORKDIR /app/bin

CMD [ "./mcr-api-server" ]
