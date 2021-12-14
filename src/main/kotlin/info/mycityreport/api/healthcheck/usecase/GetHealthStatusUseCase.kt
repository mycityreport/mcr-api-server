package info.mycityreport.api.healthcheck.usecase

import info.mycityreport.api.healthcheck.domain.entities.HTTPSuccessStatusCode

class GetHealthStatusUseCase {
    fun execute(): HealthStatusDTO {
        val status = HTTPSuccessStatusCode(200)
        return HealthStatusDTO(status)
    }
}
