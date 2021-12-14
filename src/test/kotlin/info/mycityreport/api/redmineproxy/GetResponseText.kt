package info.mycityreport.api.redmineproxy

import info.mycityreport.api.redmineproxy.domain.entities.ContentType
import info.mycityreport.api.redmineproxy.domain.entities.GetResponse
import info.mycityreport.api.redmineproxy.domain.entities.HTTPStatusCode
import info.mycityreport.api.redmineproxy.domain.entities.ResponseBody
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

internal class GetResponseText {
    @Test
    fun `HTTPStatusCodeが正常な範囲内で生成できる`() {
        // given
        val statusCode100 = 100
        val statusCode599 = 599

        // when
        val valid100Status = HTTPStatusCode(statusCode100)
        val valid599Status = HTTPStatusCode(statusCode599)

        // then
        assertEquals(statusCode100, valid100Status.value)
        assertEquals(statusCode599, valid599Status.value)
    }

    @Test
    fun `異常なHTTPStatusCodeは生成できない`() {
        // given
        val invalid99Status = 99
        val invalid600Status = 600

        // when
        val exception99 = assertThrows<IllegalArgumentException> { HTTPStatusCode(invalid99Status) }
        val exception600 = assertThrows<IllegalArgumentException> { HTTPStatusCode(invalid600Status) }

        // then
        assertEquals("HTTP Response Status Code must be between 100 to 599", exception99.message)
        assertEquals("HTTP Response Status Code must be between 100 to 599", exception600.message)
    }

    @Test
    fun `正常なContentTypeが生成できる`() {
        // given
        val contentTypeString = "application/json"

        // when
        val contentType = ContentType(contentTypeString)

        // then
        assertEquals(contentTypeString, contentType.value)
    }

    @Test
    fun `異常なContentTypeは生成できない`() {
        // given
        val invalidContentTypeString = "application_json"

        // when
        val invalidContentType = assertThrows<IllegalArgumentException> { ContentType(invalidContentTypeString) }

        // then
        assertEquals("Invalid Content Type", invalidContentType.message)
    }

    @Test
    fun `正常なGETResponseが生成できる`() {
        // given
        val statusCode = HTTPStatusCode(200)
        val contentType = ContentType("application/json")
        val responseBody = ResponseBody("{\"foo\": \"bar\"}".toByteArray())

        // when
        val getResponse = GetResponse(statusCode, contentType, responseBody)

        // then
        assertEquals(statusCode.value, getResponse.statusCode)
        assertEquals(contentType.value, getResponse.contentType)
        assertEquals(responseBody.value, getResponse.body)
    }
}
