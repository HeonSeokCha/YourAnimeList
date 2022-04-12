package com.chs.youranimelist.data

sealed class NetworkState<T>(
    val data: T? = null,
    val message: String? = null,
) {
    class Success<T>(data: T) : NetworkState<T>(data)
    class Loading<T>(data: T? = null) : NetworkState<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkState<T>(data, message)
}