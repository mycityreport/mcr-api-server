package info.mycityreport.api.redmineproxy

import info.mycityreport.api.redmineproxy.domain.entities.GETParameter
import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.URLPath
import info.mycityreport.api.redmineproxy.usecase.GetProxyUseCase
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class GetProxyUseCaseTest {
    @Test
    fun `必須情報を渡すとGETレスポンスがプロキシ先から返ってくる`() {
        // given
        val client = DummyGetHTTPClient(200)
        val useCase = GetProxyUseCase(client)
        val path = URLPath("/issues.json")
        val headers = mapOf("Host" to listOf("localhost")).map { HTTPHeader(it.key, it.value) }
        val params = mapOf("project_id" to listOf("3")).map { GETParameter(it.key, it.value) }

        // when
        val response = runBlocking { useCase.execute(path, headers, params) }

        // then
        assertEquals(200, response.statusCode)
        assertEquals("text/plain", response.contentType)
        assertTrue { path.value.toByteArray().contentEquals(response.responseBody) }
    }

    @Test
    fun `プロキシ先で起こったエラーがそのまま返ってくる`() {
        // given
        val client = DummyGetHTTPClient(404)
        val useCase = GetProxyUseCase(client)
        val path = URLPath("/issues.json")
        val headers = mapOf("Host" to listOf("localhost")).map { HTTPHeader(it.key, it.value) }
        val params = mapOf("project_id" to listOf("3")).map { GETParameter(it.key, it.value) }

        // when
        val response = runBlocking { useCase.execute(path, headers, params) }

        // then
        assertEquals(404, response.statusCode)
        assertEquals("text/plain", response.contentType)
        assertTrue { path.value.toByteArray().contentEquals(response.responseBody) }
    }
}
