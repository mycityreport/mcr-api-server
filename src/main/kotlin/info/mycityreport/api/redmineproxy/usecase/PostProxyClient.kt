package info.mycityreport.api.redmineproxy.usecase

import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.HTTPResponse
import info.mycityreport.api.redmineproxy.domain.entities.RequestBody
import info.mycityreport.api.redmineproxy.domain.entities.URLPath

interface PostProxyClient {
    suspend fun post(
        path: URLPath,
        httpHeaders: List<HTTPHeader>,
        requestBody: RequestBody,
    ): HTTPResponse
}
