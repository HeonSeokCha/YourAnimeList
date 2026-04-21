package com.chs.youranimelist.di

import android.content.Context
import com.chs.youranimelist.data.source.db.AnimeListDatabase
import com.chs.youranimelist.data.source.db.getDatabaseBuilder
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.ksp.generated.module

@Module
class AndroidPlatformModule {
    @Single
    fun provideDatabase(context: Context): AnimeListDatabase = getDatabaseBuilder(context)

    @Single
    fun provideHttpClientEngine(): HttpClientEngine = OkHttp.create()
}

actual val platformModule : org.koin.core.module.Module
    get() = AndroidPlatformModule().module