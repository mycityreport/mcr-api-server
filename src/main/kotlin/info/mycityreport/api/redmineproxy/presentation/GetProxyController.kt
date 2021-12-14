package info.mycityreport.api.redmineproxy.presentation

import info.mycityreport.api.redmineproxy.usecase.GetProxyUseCase

class GetProxyController(private val useCase: GetProxyUseCase) {
    suspend fun callGetProxy(path: String) {
        val response = useCase.execute(path)
    }
}
