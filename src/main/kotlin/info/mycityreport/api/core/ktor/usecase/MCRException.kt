package info.mycityreport.api.core.ktor.usecase

sealed class MCRException(message: String) : Exception(message) {
    object AuthorizationException : MCRException("You have not authorized for this resource.")
}
