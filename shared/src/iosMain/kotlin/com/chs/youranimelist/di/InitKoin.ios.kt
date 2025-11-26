package com.chs.youranimelist.di

import com.chs.youranimelist.data.source.db.DatabaseFactory
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val platformModule = module {
    single { Darwin.create() }
    single { DatabaseFactory() }
}