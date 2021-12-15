package info.mycityreport.api.redmineproxy.usecase

import info.mycityreport.api.redmineproxy.domain.entities.GETParameter
import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.HTTPResponse
import info.mycityreport.api.redmineproxy.domain.entities.URLPath

interface GetProxyClient {
    suspend fun get(
        path: URLPath,
        httpHeaders: List<HTTPHeader>,
        getParams: List<GETParameter>,
    ): HTTPResponse
}
