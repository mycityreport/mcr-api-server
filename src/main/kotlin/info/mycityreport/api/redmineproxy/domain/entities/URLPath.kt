package info.mycityreport.api.redmineproxy.domain.entities

@JvmInline
value class URLPath(val value: String) {
    init {
        require(value.startsWith("/")) { "Path must start with slash." }
    }
}

data class HTTPHeader(val key: String, val values: List<String>)

data class GETParameter(val key: String, val values: List<String>)

@JvmInline
value class RequestBody(val value: ByteArray)
