package com.chs.youranimelist.di

import com.chs.youranimelist.data.source.db.DatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual fun platformModule() = module {
    single { DatabaseFactory() }
    single<HttpClientEngine> { Darwin.create() }
}