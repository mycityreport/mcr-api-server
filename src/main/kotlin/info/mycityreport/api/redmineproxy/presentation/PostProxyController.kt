package info.mycityreport.api.redmineproxy.presentation

import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.RequestBody
import info.mycityreport.api.redmineproxy.domain.entities.URLPath
import info.mycityreport.api.redmineproxy.usecase.PostProxyResponse
import info.mycityreport.api.redmineproxy.usecase.PostProxyUseCase
import io.ktor.request.ApplicationRequest
import io.ktor.request.path
import io.ktor.util.toByteArray
import io.ktor.util.toMap

class PostProxyController(private val useCase: PostProxyUseCase, private val request: ApplicationRequest) {
    suspend fun callPostProxy(): PostProxyResponse {
        val rawPath = request.path().pathParser()
        val rawHeaders = request.headers.toMap()
        val rawBody = request.receiveChannel().toByteArray()
        val path = URLPath(rawPath)
        val headers = rawHeaders.map { HTTPHeader(it.key, it.value) }
        val body = RequestBody(rawBody)
        return useCase.execute(path, headers, body)
    }
}
