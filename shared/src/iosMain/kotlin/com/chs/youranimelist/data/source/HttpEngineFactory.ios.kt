package com.chs.youranimelist.data.source

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual class HttpEngineFactory actual constructor() {
    actual fun create(): HttpClientEngine = Darwin.create()
}