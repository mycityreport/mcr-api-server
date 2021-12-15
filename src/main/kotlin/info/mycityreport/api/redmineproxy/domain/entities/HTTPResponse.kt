package info.mycityreport.api.redmineproxy.domain.entities

@JvmInline
value class HTTPStatusCode(val value: Int) {
    init {
        require(value in 100..599) { "HTTP Response Status Code must be between 100 to 599" }
    }
}

@JvmInline
value class ContentType(val value: String) {
    init {
        val parts = value.split("/")
        require(parts.size == 2 && parts.all { it.isNotEmpty() }) { "Invalid Content Type" }
    }
}

@JvmInline
value class ResponseBody(val value: ByteArray)

data class HTTPResponse(
    private val _statusCode: HTTPStatusCode,
    private val _contentType: ContentType,
    private val _body: ResponseBody,
) {
    val statusCode: Int
        get() = _statusCode.value
    val contentType: String
        get() = _contentType.value
    val body: ByteArray
        get() = _body.value
}
