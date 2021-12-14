package info.mycityreport.api.redmineproxy.usecase

import info.mycityreport.api.redmineproxy.domain.entities.GETParameter
import info.mycityreport.api.redmineproxy.domain.entities.GetResponse
import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.URLPath

class GetProxyUseCase(private val getProxyClient: GetProxyClient) {
    suspend fun execute(
        path: URLPath,
        headers: List<HTTPHeader>,
        params: List<GETParameter>
    ): GetResponse {
        return getProxyClient.get(path, headers, params)
    }
}
