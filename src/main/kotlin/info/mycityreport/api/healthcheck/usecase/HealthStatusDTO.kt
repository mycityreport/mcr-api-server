package info.mycityreport.api.healthcheck.usecase

import info.mycityreport.api.healthcheck.domain.entities.HTTPSuccessStatusCode

data class HealthStatusDTO(private val status: HTTPSuccessStatusCode) {
    fun status(): Int {
        return this.status.value
    }
}
