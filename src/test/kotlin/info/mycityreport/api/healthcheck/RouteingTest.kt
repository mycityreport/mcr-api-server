package info.mycityreport.api.healthcheck

import info.mycityreport.api.module
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals

internal class RouteingTest {
    @Test
    fun healthEndpointIsAvailable() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/health").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("{\"status\":200}", response.content)
            }
        }
    }
}
