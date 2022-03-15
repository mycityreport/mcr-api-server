package info.mycityreport.api.redmineproxy.presentation

import info.mycityreport.api.redmineproxy.domain.entities.GETParameter
import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.URLPath
import info.mycityreport.api.redmineproxy.usecase.GetProxyResponse
import info.mycityreport.api.redmineproxy.usecase.GetProxyUseCase
import io.ktor.request.ApplicationRequest
import io.ktor.request.path
import io.ktor.util.toMap

class GetProxyController(private val useCase: GetProxyUseCase, private val request: ApplicationRequest) {
    suspend fun callGetProxy(): GetProxyResponse {
        val rawPath = request.path().pathParser()
        val rawHeaders = request.headers.toMap()
        val rawParams = request.queryParameters.toMap()
        val path = URLPath(rawPath)
        val headers = rawHeaders.map { HTTPHeader(it.key, it.value) }
        val params = rawParams.map { GETParameter(it.key, it.value) }
        return useCase.execute(path, headers, params)
    }
}
