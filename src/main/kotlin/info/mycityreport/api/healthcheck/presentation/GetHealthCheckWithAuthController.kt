package info.mycityreport.api.healthcheck.presentation

import info.mycityreport.api.core.ktor.usecase.Permission
import info.mycityreport.api.core.ktor.usecase.Resource
import info.mycityreport.api.core.ktor.usecase.ResourcePermission
import info.mycityreport.api.healthcheck.usecase.GetHealthStatusUseCaseWithAuth

class GetHealthCheckWithAuthController(private val useCase: GetHealthStatusUseCaseWithAuth) {
    fun responseHealthCheckStatus(permissions: List<String>): HealthCheckStatus {
        val perms = permissions.map {
            val (r, p) = it.split(":")
            ResourcePermission(Resource(r), Permission(p))
        }
        val status = this.useCase.execute(perms)
        return HealthCheckStatus(status.status())
    }
}
