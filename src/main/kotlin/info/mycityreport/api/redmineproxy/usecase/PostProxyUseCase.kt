package info.mycityreport.api.redmineproxy.usecase

import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.RequestBody
import info.mycityreport.api.redmineproxy.domain.entities.URLPath

class PostProxyUseCase(private val postProxyClient: PostProxyClient) {
    suspend fun execute(path: URLPath, headers: List<HTTPHeader>, body: RequestBody): PostProxyResponse {
        val response = postProxyClient.post(path, headers, body)
        return PostProxyResponse(response.statusCode, response.contentType, response.body)
    }
}

data class PostProxyResponse(
    val statusCode: Int,
    val contentType: String,
    val responseBody: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostProxyResponse

        if (statusCode != other.statusCode) return false
        if (contentType != other.contentType) return false
        if (!responseBody.contentEquals(other.responseBody)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = statusCode
        result = 31 * result + contentType.hashCode()
        result = 31 * result + responseBody.contentHashCode()
        return result
    }
}
