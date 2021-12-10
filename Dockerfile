FROM gcr.io/distroless/java17-debian11:nonroot

EXPOSE 8080

WORKDIR /app

COPY ./build/libs/mcr-api-server-*-all.jar mcr-api-server.jar

CMD [ "mcr-api-server.jar" ]
