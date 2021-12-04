package info.mycityreport.api.healthcheck.presentation

import info.mycityreport.api.healthcheck.usecase.GetHealthStatusUseCase
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.healthCheckRouting() {
    val useCase = GetHealthStatusUseCase()
    val getController = GetHealthCheckController(useCase)

    route("/health") {
        get {
            call.respond(getController.responseHealthCheckStatus())
        }
    }
}

fun Application.registerHealthCheckRoutes() {
    routing {
        healthCheckRouting()
    }
}
