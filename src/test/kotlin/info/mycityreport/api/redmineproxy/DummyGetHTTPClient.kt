package info.mycityreport.api.redmineproxy

import info.mycityreport.api.redmineproxy.domain.entities.ContentType
import info.mycityreport.api.redmineproxy.domain.entities.GETParameter
import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.HTTPResponse
import info.mycityreport.api.redmineproxy.domain.entities.HTTPStatusCode
import info.mycityreport.api.redmineproxy.domain.entities.ResponseBody
import info.mycityreport.api.redmineproxy.domain.entities.URLPath
import info.mycityreport.api.redmineproxy.usecase.GetProxyClient

class DummyGetHTTPClient(private val status: Int) : GetProxyClient {
    override suspend fun get(
        path: URLPath,
        httpHeaders: List<HTTPHeader>,
        getParams: List<GETParameter>
    ): HTTPResponse {
        val statusCode = HTTPStatusCode(status)
        val contentType = ContentType("text/plain")
        val body = ResponseBody(path.value.toByteArray())
        return HTTPResponse(statusCode, contentType, body)
    }
}
