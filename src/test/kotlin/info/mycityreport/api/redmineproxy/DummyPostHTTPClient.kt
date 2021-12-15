package info.mycityreport.api.redmineproxy

import info.mycityreport.api.redmineproxy.domain.entities.ContentType
import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.HTTPResponse
import info.mycityreport.api.redmineproxy.domain.entities.HTTPStatusCode
import info.mycityreport.api.redmineproxy.domain.entities.RequestBody
import info.mycityreport.api.redmineproxy.domain.entities.ResponseBody
import info.mycityreport.api.redmineproxy.domain.entities.URLPath
import info.mycityreport.api.redmineproxy.usecase.PostProxyClient

class DummyPostHTTPClient(private val status: Int, private val response: String) : PostProxyClient {
    override suspend fun post(path: URLPath, httpHeaders: List<HTTPHeader>, requestBody: RequestBody): HTTPResponse {
        val statusCode = HTTPStatusCode(status)
        val contentType = ContentType("application/json")
        val responseBody = ResponseBody(response.toByteArray())

        return HTTPResponse(statusCode, contentType, responseBody)
    }
}
