package info.mycityreport.api.core.ktor

import java.net.MalformedURLException
import java.net.URL

class CORSSettings(rawCORSSettings: String) {
    private val rawURLs: List<String> = rawCORSSettings.split(",")

    fun allowedURLs(): List<URL> {
        return rawURLs.mapNotNull {
            try {
                URL(it)
            } catch (e: MalformedURLException) {
                null
            }
        }
    }
}
