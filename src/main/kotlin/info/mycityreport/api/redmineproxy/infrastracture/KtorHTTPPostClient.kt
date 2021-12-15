package info.mycityreport.api.redmineproxy.infrastracture

import info.mycityreport.api.redmineproxy.domain.entities.ContentType
import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.HTTPResponse
import info.mycityreport.api.redmineproxy.domain.entities.HTTPStatusCode
import info.mycityreport.api.redmineproxy.domain.entities.RequestBody
import info.mycityreport.api.redmineproxy.domain.entities.ResponseBody
import info.mycityreport.api.redmineproxy.domain.entities.URLPath
import info.mycityreport.api.redmineproxy.usecase.PostProxyClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.contentType
import io.ktor.util.toByteArray

class KtorHTTPPostClient(private val baseURL: String) : PostProxyClient {
    private val defaultContentType = "text/plain"

    override suspend fun post(path: URLPath, httpHeaders: List<HTTPHeader>, requestBody: RequestBody): HTTPResponse {
        val client = HttpClient(CIO) { expectSuccess = false }
        val response = client.post<HttpResponse>("$baseURL${path.value}") {
            headers {
                httpHeaders.filterNot { it.key == "Host" }.forEach { appendAll(it.key, it.values) }
            }
            body = requestBody
        }
        client.close()

        val statusCode = HTTPStatusCode(response.status.value)
        val contentType = ContentType(response.contentType()?.toString() ?: defaultContentType)
        val body = ResponseBody(response.content.toByteArray())

        return HTTPResponse(statusCode, contentType, body)
    }
}
