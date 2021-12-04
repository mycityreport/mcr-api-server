package info.mycityreport.api.healthcheck

import info.mycityreport.api.healthcheck.usecase.GetHealthStatusUseCase
import kotlin.test.Test
import kotlin.test.assertEquals

internal class GetHealthStatusUseCaseTest {
    @Test
    fun `HealthCheck の結果が200となる`() {
        // given
        val expectStatusCode = 200

        // when
        val useCase = GetHealthStatusUseCase()

        // then
        assertEquals(expectStatusCode, useCase.execute().status())
    }
}
