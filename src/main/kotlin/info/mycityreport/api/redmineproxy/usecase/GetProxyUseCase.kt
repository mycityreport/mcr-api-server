package info.mycityreport.api.redmineproxy.usecase

import info.mycityreport.api.redmineproxy.domain.entities.GETParameter
import info.mycityreport.api.redmineproxy.domain.entities.GetResponse
import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.URLPath

class GetProxyUseCase(private val getProxyClient: GetProxyClient) {
    suspend fun execute(
        rawPath: String,
        rawHeaders: Map<String, List<String>>? = null,
        rawParams: Map<String, List<String>>? = null
    ): GetResponse {
        val path = URLPath(rawPath)
        val headers = rawHeaders?.map { HTTPHeader(it.key, it.value) }
        val params = rawParams?.map { GETParameter(it.key, it.value) }
        return getProxyClient.get(path, headers, params)
    }
}
