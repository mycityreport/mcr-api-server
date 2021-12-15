package info.mycityreport.api.redmineproxy

import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.RequestBody
import info.mycityreport.api.redmineproxy.domain.entities.URLPath
import info.mycityreport.api.redmineproxy.usecase.PostProxyUseCase
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class PostProxyUseCaseTest {
    @Test
    fun `必要情報を渡すとPOSTの結果が返ってくる`() {
        // given
        val responseMessage = "Success"
        val client = DummyPostHTTPClient(201, responseMessage)
        val useCase = PostProxyUseCase(client)
        val path = URLPath("/issues.json")
        val headers = mapOf("Host" to listOf("localhost")).map { HTTPHeader(it.key, it.value) }
        val body = RequestBody("".toByteArray())

        // when
        val response = runBlocking {
            useCase.execute(path, headers, body)
        }

        // then
        assertEquals(201, response.statusCode)
        assertEquals("application/json", response.contentType)
        assertTrue { responseMessage.toByteArray().contentEquals(response.responseBody) }
    }

    @Test
    fun `Proxy先でのエラーがそのまま返ってくる`() {
        // given
        val responseMessage = "Failed"
        val client = DummyPostHTTPClient(400, responseMessage)
        val useCase = PostProxyUseCase(client)
        val path = URLPath("/issues.json")
        val headers = mapOf("Host" to listOf("localhost")).map { HTTPHeader(it.key, it.value) }
        val body = RequestBody("".toByteArray())

        // when
        val response = runBlocking {
            useCase.execute(path, headers, body)
        }

        // then
        assertEquals(400, response.statusCode)
        assertEquals("application/json", response.contentType)
        assertTrue { responseMessage.toByteArray().contentEquals(response.responseBody) }
    }
}
