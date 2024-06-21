package com.chs.data.source

import android.util.Log
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import okhttp3.Interceptor
import okhttp3.Response

class TestInterCeptor : HttpInterceptor {
    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain
    ): HttpResponse {
        val a = chain.proceed(request)
        Log.e("CHS_LOG", a.headers[4].toString())
        return a
    }
}