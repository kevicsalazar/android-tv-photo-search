package dev.kevinsalazar.tv.domain.values


import dev.kevinsalazar.tv.domain.errors.Error as RootError

sealed interface Result<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Result<D, E>
    data class Error<out D, out E : RootError>(val error: E, val e: Throwable) : Result<D, E>
}

inline fun <D, E : RootError> Result<D, E>.onFailure(action: (exception: RootError, e: Throwable) -> Unit): Result<D, E> {
    if (this is Result.Error) action.invoke(error, e)
    return this
}

inline fun <D, E : RootError> Result<D, E>.onSuccess(action: (value: D) -> Unit): Result<D, E> {
    if (this is Result.Success) action(data)
    return this
}