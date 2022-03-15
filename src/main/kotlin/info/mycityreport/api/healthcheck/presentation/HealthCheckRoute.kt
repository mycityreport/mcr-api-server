package info.mycityreport.api.healthcheck.presentation

import info.mycityreport.api.healthcheck.usecase.GetHealthStatusUseCase
import info.mycityreport.api.healthcheck.usecase.GetHealthStatusUseCaseWithAuth
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.principal
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing

fun Route.healthCheckRouting() {
    val useCase = GetHealthStatusUseCase()
    val getController = GetHealthCheckController(useCase)

    val authUseCase = GetHealthStatusUseCaseWithAuth()
    val authGetController = GetHealthCheckWithAuthController(authUseCase)

    route("/health") {
        get {
            call.respond(getController.responseHealthCheckStatus())
        }
    }

    route("/health/auth") {
        authenticate("auth0") {
            get {
                val principal = call.principal<JWTPrincipal>()
                val permissions = principal?.payload?.getClaim("permissions")?.asList(String::class.java)?.toList() ?: listOf()
                call.respond(authGetController.responseHealthCheckStatus(permissions))
            }
        }
    }
}

fun Application.registerHealthCheckRoutes() {
    routing {
        healthCheckRouting()
    }
}
