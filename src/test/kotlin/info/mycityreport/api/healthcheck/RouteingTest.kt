package info.mycityreport.api.healthcheck

import info.mycityreport.api.module
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

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
