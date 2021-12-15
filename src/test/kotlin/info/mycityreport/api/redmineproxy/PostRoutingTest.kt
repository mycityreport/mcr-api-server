package info.mycityreport.api.redmineproxy

import info.mycityreport.api.module
import info.mycityreport.api.redmineproxy.usecase.PostProxyClient
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PostRoutingTest {
    @Test
    fun `プロキシ対象のアドレスにPOSTできる`() {
        // given
        val dependencies = DI {
            bind<PostProxyClient>() { singleton { DummyPostHTTPClient(201) } }
        }
        val path = "/proxy/issues.json"
        val body = "{\"name\": \"sample\"}"

        // when

        // then
        withTestApplication({ module(dependencies) }) {
            handleRequest(HttpMethod.Post, path) {
                setBody(body)
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
    }
}