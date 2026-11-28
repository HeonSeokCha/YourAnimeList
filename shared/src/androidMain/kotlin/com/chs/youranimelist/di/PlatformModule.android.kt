package com.chs.youranimelist.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.chs.youranimelist.data.source.db.AnimeListDatabase
import com.chs.youranimelist.util.Constants.DATA_STORE_FILE_NAME
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.Dispatchers
import okio.Path.Companion.toPath
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

    @Single
    fun providePref(context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createWithPath {
            context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath.toPath()
        }
    }
}