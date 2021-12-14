package info.mycityreport.api.redmineproxy.domain.entities

@JvmInline
value class URLPath(val value: String)

data class HTTPHeader(val key: String, val values: List<String>)

data class GETParameter(val key: String, val values: List<String>)
