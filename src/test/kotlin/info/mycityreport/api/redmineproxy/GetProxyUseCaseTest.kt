package info.mycityreport.api.redmineproxy

import info.mycityreport.api.redmineproxy.usecase.GetProxyUseCase
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class GetProxyUseCaseTest {
    private val client = DummyGetHTTPClient()
    private val useCase = GetProxyUseCase(client)

    @Test
    fun `必須情報を渡すとGETレスポンスがプロキシ先から返ってくる`() {
        // given
        val path = "issues.json"
        val headers = mapOf("Host" to listOf("localhost"))
        val params = mapOf("project_id" to listOf("3"))

        // when
        val response = runBlocking { useCase.execute(path, headers, params) }

        // then
        assertEquals(200, response.statusCode)
        assertEquals("text/plain", response.contentType)
        assertTrue { path.toByteArray().contentEquals(response.body) }
    }
}
