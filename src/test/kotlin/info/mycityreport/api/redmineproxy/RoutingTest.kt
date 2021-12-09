package info.mycityreport.api.redmineproxy

import info.mycityreport.api.module
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals

internal class RoutingTest {
    @Test
    fun `プロキシ対象の各種アドレスにアクセスできる`() {
        // given
        val urls = listOf("/issues.json", "/projects/3.json", "/my/account")

        // when

        // then
        withTestApplication(Application::module) {
            urls.forEach {
                handleRequest(HttpMethod.Get, it).apply {
                    assertEquals(HttpStatusCode.OK, response.status())
                }
            }
        }
    }
}
