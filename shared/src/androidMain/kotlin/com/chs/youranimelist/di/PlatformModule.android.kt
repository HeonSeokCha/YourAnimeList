package com.chs.youranimelist.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.chs.youranimelist.data.source.db.AnimeListDatabase
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
actual class PlatformModule {

    @Single
    fun provideDatabase(context: Context): AnimeListDatabase {
        val dbFile = context.getDatabasePath(AnimeListDatabase.DB_NAME)
        return Room.databaseBuilder<AnimeListDatabase>(context, dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    @Single
    fun provideHttpClientEngine(): HttpClientEngine = OkHttp.create()
}