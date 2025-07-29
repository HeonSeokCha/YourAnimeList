package com.chs.youranimelist.util

interface Error

sealed interface DataError : Error {
    data class RemoteError(val message: String?) : DataError
}


sealed interface DataResult<out D, out E : Error> {
    data class Success<out D>(val data: D) : DataResult<D, Nothing>
    data class Error<out E : com.chs.youranimelist.util.Error>(val error: E) : DataResult<Nothing, E>
}

inline fun <T, E : Error, R> DataResult<T, E>.map(map: (T) -> R): DataResult<R, E> {
    return when (this) {
        is DataResult.Error -> DataResult.Error(error)
        is DataResult.Success -> DataResult.Success(map(data))
    }
}

fun <T, E : Error> DataResult<T, E>.asEmptyDataDataResult(): EmptyResult<E> {
    return map { }
}

inline fun <T, E : Error> DataResult<T, E>.onSuccess(action: (T) -> Unit): DataResult<T, E> {
    return when (this) {
        is DataResult.Error -> this
        is DataResult.Success -> {
            action(data)
            this
        }
    }
}

inline fun <T, E : Error> DataResult<T, E>.onError(action: (E) -> Unit): DataResult<T, E> {
    return when (this) {
        is DataResult.Error -> {
            action(error)
            this
        }

        is DataResult.Success -> this
    }
}

typealias EmptyResult<E> = DataResult<Unit, E>
