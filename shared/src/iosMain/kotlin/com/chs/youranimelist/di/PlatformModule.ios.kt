package com.chs.youranimelist.di

import com.chs.youranimelist.data.source.db.AnimeListDatabase
import com.chs.youranimelist.data.source.db.getDatabaseBuilder
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.annotation.Module

@Module
actual class PlatformModule {
    fun provideHttpClientEngine(): HttpClientEngine = Darwin.create()

    fun provideDatabase(): AnimeListDatabase = getDatabaseBuilder()
}