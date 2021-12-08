package info.mycityreport.api

import info.mycityreport.api.healthcheck.presentation.registerHealthCheckRoutes
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.serialization.json

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(CORS) {
        anyHost()
    }
    install(ContentNegotiation) {
        json()
        registerHealthCheckRoutes()
    }
    routing {
        get("/") {
            call.respondText("Hello World.")
        }
    }
}
