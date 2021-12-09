package info.mycityreport.api

import info.mycityreport.api.core.ktor.CORSSettings
import info.mycityreport.api.healthcheck.presentation.registerHealthCheckRoutes
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpMethod
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.serialization.json

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(CORS) {
        val rawCORSSettings = environment.config.propertyOrNull("ktor.corsAllowedURLs")?.getString() ?: ""
        val corsSettings = CORSSettings(rawCORSSettings)
        if (corsSettings.allowedURLs().isEmpty()) {
            anyHost()
        } else {
            corsSettings.allowedURLs().forEach {
                host(it.authority, schemes = listOf(it.protocol))
            }
        }
        // Default allowed HTTP methods are GET, POST and HEAD.
        // https://ktor.io/docs/cors.html#methods
        method(HttpMethod.Options)

        // Default allowed HTTP Headers are
        // Accept, Accept-Language, Content-Language, and Content-Type
        // https://ktor.io/docs/cors.html#headers
        allowHeadersPrefixed("X-Redmine")
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
