package info.mycityreport.api

import info.mycityreport.api.healthcheck.presentation.registerHealthCheckRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
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
