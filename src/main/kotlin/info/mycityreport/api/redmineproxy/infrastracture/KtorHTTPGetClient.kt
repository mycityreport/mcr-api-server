package info.mycityreport.api.redmineproxy.infrastracture

import info.mycityreport.api.redmineproxy.domain.entities.GETParameter
import info.mycityreport.api.redmineproxy.domain.entities.GetResponse
import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.URLPath
import info.mycityreport.api.redmineproxy.usecase.GetProxyClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.contentType
import io.ktor.util.toByteArray

class KtorHTTPGetClient(private val baseURL: String) : GetProxyClient {
    private val defaultContentType = "text/plain"

    override suspend fun get(path: URLPath, httpHeaders: List<HTTPHeader>?, getParams: List<GETParameter>?): GetResponse {
        val client = HttpClient(CIO)
        val response = client.get<HttpResponse>("$baseURL/${path.value}") {
            headers {
                httpHeaders?.filterNot { it.key == "Host" }?.forEach { appendAll(it.key, it.values) }
            }
            getParams?.forEach { param -> param.values.forEach { parameter(param.key, it) } }
        }
        client.close()
        return GetResponse(
            response.status.value,
            response.contentType()?.toString() ?: defaultContentType,
            response.content.toByteArray(),
        )
    }
}
