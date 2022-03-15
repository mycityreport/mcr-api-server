package info.mycityreport.api

import info.mycityreport.api.core.ktor.CORSSettings
import info.mycityreport.api.healthcheck.presentation.registerHealthCheckRoutes
import info.mycityreport.api.redmineproxy.infrastracture.KtorHTTPGetClient
import info.mycityreport.api.redmineproxy.infrastracture.KtorHTTPPostClient
import info.mycityreport.api.redmineproxy.presentation.registerRedmineProxyRoutes
import info.mycityreport.api.redmineproxy.usecase.GetProxyClient
import info.mycityreport.api.redmineproxy.usecase.PostProxyClient
import io.ktor.application.Application
import io.ktor.application.ApplicationEnvironment
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpMethod
import io.ktor.serialization.json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun dependencies(environment: ApplicationEnvironment): DI {
    val defaultBaseURL = "http://localhost:5000"
    val baseURL = environment.config.propertyOrNull("ktor.mcr.proxyBaseURL")?.getString() ?: defaultBaseURL
    return DI {
        bind<GetProxyClient> { singleton { KtorHTTPGetClient(baseURL) } }
        bind<PostProxyClient> { singleton { KtorHTTPPostClient(baseURL) } }
    }
}

fun Application.module(dependencies: DI = dependencies(environment)) {
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
    di {
        extend(dependencies)
    }
    install(ContentNegotiation) {
        json()
        registerHealthCheckRoutes()
        registerRedmineProxyRoutes()
    }
}
