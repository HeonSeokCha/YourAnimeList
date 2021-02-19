package com.chs.youranimelist.network

sealed class NetWorkState<T>(
    val responseState: ResponseState,
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : NetWorkState<T>(ResponseState.SUCCESS, data)
    class Loading<T>(data: T? = null) : NetWorkState<T>(ResponseState.LOADING, data)
    class Error<T>(message: String, data: T? = null) :
        NetWorkState<T>(ResponseState.ERROR, data, message)
}

enum class ResponseState {
    SUCCESS,
    LOADING,
    ERROR
}