package info.mycityreport.api.core.ktor

import io.ktor.auth.jwt.JWTCredential
import io.ktor.auth.jwt.JWTPrincipal

fun JWTCredential.validateCredentialsOrNull(audience: String): JWTPrincipal? {
    val containsAudience = this.payload.audience.contains(audience)
    return if (containsAudience) {
        JWTPrincipal(this.payload)
    } else {
        null
    }
}
