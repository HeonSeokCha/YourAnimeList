package com.chs.data.source

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class TestInterCeptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val a = chain.proceed(chain.request())

//        Log.e("CHS_LOG", "${a.headers["X-RateLimit-Remaining"]} / ${a.headers["X-RateLimit-Limit"]}")

        return a
    }
}