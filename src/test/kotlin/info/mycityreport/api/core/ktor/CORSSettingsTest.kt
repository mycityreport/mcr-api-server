package info.mycityreport.api.core.ktor

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class CORSSettingsTest {
    @Test
    fun `設定が空文字列の場合は空配列が返ってくる`() {
        // given
        val emptySetting = ""

        // when
        val parsedSettings = CORSSettings(emptySetting)

        // then
        assertTrue { parsedSettings.allowedURLs().isEmpty() }
    }

    @Test
    fun `URLが1つ設定されているときは要素1のリストとなる`() {
        // given
        val url = "http://localhost:5000"

        // when
        val parsedSettings = CORSSettings(url)

        // then
        assertEquals(1, parsedSettings.allowedURLs().size)

        parsedSettings.allowedURLs().forEach {
            assertEquals("http", it.protocol)
            assertEquals("localhost:5000", it.authority)
        }
    }

    @Test
    fun `URLがカンマ区切りで2つ設定されているときに正しくパースされる`() {
        // given
        val urls = "http://localhost:5000,http://www.example.com"

        // when
        val parsedSettings = CORSSettings(urls)

        // then
        assertEquals(2, parsedSettings.allowedURLs().size)
    }

    @Test
    fun `URLでないものが混入したときはそれを除外したものだけを有効とする`() {
        // given
        val invalidURLs = "hogehoge,http://localhost:5000,fugafuga"

        // when
        val parsedSettings = CORSSettings(invalidURLs)

        // then
        assertEquals(1, parsedSettings.allowedURLs().size)
    }
}
