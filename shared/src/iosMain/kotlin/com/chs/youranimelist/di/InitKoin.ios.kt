package com.chs.youranimelist.di

import com.chs.youranimelist.data.source.HttpEngineFactory
import com.chs.youranimelist.data.source.db.DatabaseFactory
import org.koin.dsl.module

actual val platformModule = module {
    single { HttpEngineFactory() }
    single { DatabaseFactory() }
}