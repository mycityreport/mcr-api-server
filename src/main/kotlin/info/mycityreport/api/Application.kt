package info.mycityreport.api

import com.auth0.jwk.JwkProviderBuilder
import info.mycityreport.api.core.ktor.CORSSettings
import info.mycityreport.api.core.ktor.validateCredentialsOrNull
import info.mycityreport.api.healthcheck.presentation.registerHealthCheckRoutes
import info.mycityreport.api.redmineproxy.infrastracture.KtorHTTPGetClient
import info.mycityreport.api.redmineproxy.presentation.registerRedmineProxyRoutes
import info.mycityreport.api.redmineproxy.usecase.GetProxyClient
import io.ktor.application.Application
import io.ktor.application.ApplicationEnvironment
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpMethod
import io.ktor.serialization.json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import java.util.concurrent.TimeUnit

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun dependencies(environment: ApplicationEnvironment): DI {
    val defaultBaseURL = "http://localhost:5000"
    val baseURL = environment.config.propertyOrNull("ktor.mcr.proxyBaseURL")?.getString() ?: defaultBaseURL
    return DI {
        bind<GetProxyClient> { singleton { KtorHTTPGetClient(baseURL) } }
    }
}

fun Application.module(dependencies: DI = dependencies(environment)) {
    val audience = environment.config.propertyOrNull("ktor.auth0.audience")?.getString() ?: ""
    val issuer = environment.config.propertyOrNull("ktor.auth0.issuer")?.getString() ?: ""
    val jwkProvider = JwkProviderBuilder(issuer)
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    install(Authentication) {
        jwt("auth0") {
            verifier(jwkProvider, issuer)
            validate { it.validateCredentialsOrNull(audience) }
        }
    }

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
        header("authorization")

        allowCredentials = true
        allowNonSimpleContentTypes = true
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
