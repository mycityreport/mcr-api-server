FROM openjdk:17-slim-buster as builder

ARG GRADLE_OPTS

WORKDIR /app

COPY build.gradle.kts gradle.properties settings.gradle.kts gradlew ./

COPY gradle ./gradle

RUN ./gradlew dependencies --refresh-dependencies

COPY src ./src

RUN ./gradlew installDist


FROM openjdk:17-slim-buster

EXPOSE 8080

RUN mkdir /app
RUN useradd mcr
RUN chown mcr:mcr /app

COPY --from=builder --chown=mcr:mcr /app/build/install/mcr-api-server/ /app/

USER mcr

WORKDIR /app/bin

CMD [ "./mcr-api-server" ]
