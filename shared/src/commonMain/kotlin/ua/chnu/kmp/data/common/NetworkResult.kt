package ua.chnu.kmp.data.common

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerializationException

internal sealed class NetworkResult<out T>  {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure(val errorMessage: String) : NetworkResult<Nothing>()
}

/**
 * Transforms the payload of a [NetworkResult.Success] with [transform].
 * A [NetworkResult.Failure] is passed through unchanged.
 */
internal inline fun <T, R> NetworkResult<T>.map(transform: (T) -> R): NetworkResult<R> {
    return when (this) {
        is NetworkResult.Success -> NetworkResult.Success(transform(data))
        is NetworkResult.Failure -> this
    }
}

internal suspend inline fun <reified T> HttpResponse.handleResponse(): NetworkResult<T> {
    return when (status.value) {
        in 200..299 -> {
            try {
                val result = if (T::class == Unit::class) Unit as T else body<T>()
                NetworkResult.Success(result)
            } catch (e: SerializationException) {
                NetworkResult.Failure("Serialization error: ${e.message}")
            }
        }
        else -> {
            val errorBody = bodyAsText()
            NetworkResult.Failure("Error ${status.value}: $errorBody")
        }
    }
}

internal suspend inline fun <reified T> HttpClient.safeRequest(
    crossinline block: suspend HttpClient.() -> HttpResponse
): NetworkResult<T> {
    return try {
        val response = block()
        response.handleResponse<T>()
    } catch (e: Exception) {
        NetworkResult.Failure("Network error: ${e.message}")
    }
}
