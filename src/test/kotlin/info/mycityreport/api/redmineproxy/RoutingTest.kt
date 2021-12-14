package info.mycityreport.api.redmineproxy

import info.mycityreport.api.module
import info.mycityreport.api.redmineproxy.usecase.GetProxyClient
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import kotlin.test.Test
import kotlin.test.assertEquals

internal class RoutingTest {
    private val dependencies = DI {
        bind<GetProxyClient> { singleton { DummyGetHTTPClient() } }
    }

    @Test
    fun `プロキシ対象の各種アドレスにアクセスできる`() {
        // given
        val urls = listOf("/issues.json", "/projects/3.json", "/my/account")

        // when

        // then
        withTestApplication({ module(dependencies) }) {
            urls.map { "/proxy$it" }.forEach {
                handleRequest(HttpMethod.Get, it).apply {
                    assertEquals(HttpStatusCode.OK, response.status())
                }
            }
        }
    }
}
