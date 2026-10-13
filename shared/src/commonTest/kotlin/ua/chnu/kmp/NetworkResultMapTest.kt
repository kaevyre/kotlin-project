package ua.chnu.kmp

import ua.chnu.kmp.data.common.NetworkResult
import ua.chnu.kmp.data.common.map
import kotlin.test.Test
import kotlin.test.assertEquals

class NetworkResultMapTest {

    @Test
    fun mapTransformsSuccessPayload() {
        val result: NetworkResult<Int> = NetworkResult.Success(2)

        assertEquals(NetworkResult.Success("2!"), result.map { "$it!" })
    }

    @Test
    fun mapKeepsFailureUntouched() {
        val failure: NetworkResult<Int> = NetworkResult.Failure("boom")

        var called = false
        val mapped = failure.map { called = true; it + 1 }

        assertEquals(NetworkResult.Failure("boom"), mapped)
        assertEquals(false, called)
    }
}
