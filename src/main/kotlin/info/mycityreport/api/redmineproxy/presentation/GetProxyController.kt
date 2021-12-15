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
        val rawPath = pathParser(request.path())
        val rawHeaders = request.headers.toMap()
        val rawParams = request.queryParameters.toMap()
        val path = URLPath(rawPath)
        val headers = rawHeaders.map { HTTPHeader(it.key, it.value) }
        val params = rawParams.map { GETParameter(it.key, it.value) }
        return useCase.execute(path, headers, params)
    }

    private fun pathParser(rawPath: String): String {
        // Ktor で request.path() を取得すると `/proxy/{...}` という状態で取れるので
        // 前半部分の `/proxy` を切り取る処理を行う
        return rawPath.replace(Regex("^/proxy"), "")
    }
}
