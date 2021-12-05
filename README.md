# My City Report for Citizens API Server

## About this software
This is a server implementation of the service "My City Report for Citizens", available as [My City Report](https://www.mycityreport.jp/).

## Development Environment
- Java 17 or later
- Kotlin 1.6.0 or later
- Gradle 7.3 or later
- Docker & Docker Compose v2

## Launching the development environment
You can start it with Docker compose. Inside you will find the actual API server and a hot reload container.
This means that any changes you make to the source code will automatically be applied to the development server.

```bash
$ docker compose build
$ docker compose up -d
```

## License
The source code is licensed under the MIT License.
