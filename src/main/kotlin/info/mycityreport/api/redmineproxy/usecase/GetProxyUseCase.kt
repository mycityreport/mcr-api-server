package info.mycityreport.api.redmineproxy.usecase

import info.mycityreport.api.redmineproxy.domain.entities.GETParameter
import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.URLPath

class GetProxyUseCase(private val getProxyClient: GetProxyClient) {
    suspend fun execute(
        path: URLPath,
        headers: List<HTTPHeader>,
        params: List<GETParameter>
    ): GetProxyResponse {
        val response = getProxyClient.get(path, headers, params)
        return GetProxyResponse(response.statusCode, response.contentType, response.body)
    }
}

data class GetProxyResponse(
    val statusCode: Int,
    val contentType: String,
    val responseBody: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GetProxyResponse

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
