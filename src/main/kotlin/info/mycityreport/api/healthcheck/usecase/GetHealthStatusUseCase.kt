package info.mycityreport.api.healthcheck.usecase

import info.mycityreport.api.healthcheck.domain.entities.HttpStatus

class GetHealthStatusUseCase {
    fun execute(): HealthStatusDTO {
        val status = HttpStatus(200)
        return HealthStatusDTO(status)
    }
}
