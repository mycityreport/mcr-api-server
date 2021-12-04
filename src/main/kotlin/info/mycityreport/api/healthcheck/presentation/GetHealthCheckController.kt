package info.mycityreport.api.healthcheck.presentation

import info.mycityreport.api.healthcheck.usecase.GetHealthStatusUseCase
import kotlinx.serialization.Serializable

class GetHealthCheckController(private val useCase: GetHealthStatusUseCase) {
    fun responseHealthCheckStatus(): HealthCheckStatus {
        val status = this.useCase.execute()
        return HealthCheckStatus(status.status())
    }
}

@Serializable
data class HealthCheckStatus(val status: Int)
