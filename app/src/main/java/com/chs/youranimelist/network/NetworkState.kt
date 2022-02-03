package com.chs.youranimelist.network

sealed class NetWorkState<T>(
    val data: T? = null,
    val message: String? = null,
) {
    class Success<T>(data: T) : NetWorkState<T>(data)
    class Loading<T>(data: T? = null) : NetWorkState<T>(data)
    class Error<T>(message: String, data: T? = null) : NetWorkState<T>(data, message)
}