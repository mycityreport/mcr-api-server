package info.mycityreport.api.redmineproxy

import info.mycityreport.api.redmineproxy.domain.entities.GETParameter
import info.mycityreport.api.redmineproxy.domain.entities.GetResponse
import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.URLPath
import info.mycityreport.api.redmineproxy.usecase.GetProxyClient

class DummyGetHTTPClient : GetProxyClient {
    override suspend fun get(
        path: URLPath,
        httpHeaders: List<HTTPHeader>?,
        getParams: List<GETParameter>?
    ): GetResponse {
        val statusCode = 200
        val contentType = "text/plain"
        val body = path.value.toByteArray()
        return GetResponse(statusCode, contentType, body)
    }
}
