package info.mycityreport.api.healthcheck.usecase

import info.mycityreport.api.healthcheck.domain.entities.HttpStatus

data class HealthStatusDTO(private val status: HttpStatus) {
    fun status(): Int {
        return this.status.value
    }
}
