package info.mycityreport.api.redmineproxy.domain.entities

data class GetResponse(
    public val statusCode: Int,
    public val contentType: String,
    public val body: ByteArray,
)
