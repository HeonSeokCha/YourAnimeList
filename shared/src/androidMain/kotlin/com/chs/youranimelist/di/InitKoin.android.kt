package com.chs.youranimelist.di

import com.chs.youranimelist.data.source.db.DatabaseFactory
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

actual val platformModule = module {
    single { DatabaseFactory(androidApplication()) }
    single { OkHttp.create() }
}