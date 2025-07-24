package com.chs.youranimelist.data.source

import io.ktor.client.engine.HttpClientEngine

expect class HttpEngineFactory() {
    fun create(): HttpClientEngine
}