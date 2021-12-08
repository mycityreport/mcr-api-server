package info.mycityreport.api.core.ktor

import io.ktor.application.ApplicationEnvironment
import java.net.URI

class CORSSettings(private val env: ApplicationEnvironment) {
    private val rawURLs: List<String>
    init {
        val rawEnvValue = env.config.propertyOrNull("ktor.corsAllowedURLs")?.getString() ?: ""
        rawURLs = rawEnvValue.split(",")
    }
    fun allowedURLs(): List<URI> {
        return rawURLs.filter { it !== "" }.map { URI(it) }
    }
}
