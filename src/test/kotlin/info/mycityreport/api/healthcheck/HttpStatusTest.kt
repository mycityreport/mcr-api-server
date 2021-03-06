package info.mycityreport.api.healthcheck

import info.mycityreport.api.healthcheck.domain.entities.HTTPSuccessStatusCode
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

internal class HttpStatusTest {
    @Test
    fun `HTTPStatusCodeは200である`() {
        // given
        val statusCode = 200

        // when
        val status = HTTPSuccessStatusCode(statusCode)

        // then
        assertEquals(statusCode, status.value)
    }

    @Test
    fun `200以外のHTTPステータスコードは受け付けない`() {
        // given
        val statusCode = 404

        // when
        val exception = assertThrows<IllegalArgumentException> { HTTPSuccessStatusCode(statusCode) }

        // then
        assertEquals(exception.message, "HTTP Status must be 200")
    }
}
