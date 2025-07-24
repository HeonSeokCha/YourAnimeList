package com.chs.youranimelist.data.source

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual class HttpEngineFactory actual constructor() {
    actual fun create(): HttpClientEngine = OkHttp.create()
}