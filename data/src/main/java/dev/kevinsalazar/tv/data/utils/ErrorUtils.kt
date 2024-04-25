package dev.kevinsalazar.tv.data.utils

import dev.kevinsalazar.tv.domain.errors.DataError
import dev.kevinsalazar.tv.domain.values.Result
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> handleRequest(block: suspend () -> T): Result<T, DataError> {

    val result = runCatching { block.invoke() }

    return if (result.isSuccess) {
        Result.Success(checkNotNull(result.getOrNull()))
    } else {
        val exception = checkNotNull(result.exceptionOrNull())
        val error = when (exception) {
            is HttpException -> {
                when (exception.code()) {
                    UNAUTHORIZED_CODE -> DataError.Network.UNAUTHORIZED
                    SERVER_ERROR_CODE -> DataError.Network.SERVER_ERROR
                    else -> DataError.Network.UNKNOWN
                }
            }

            is IOException -> DataError.Network.NO_INTERNET
            is SerializationException -> DataError.Network.SERIALIZATION
            else -> DataError.Network.UNKNOWN
        }
        Result.Error(error, exception)
    }
}
