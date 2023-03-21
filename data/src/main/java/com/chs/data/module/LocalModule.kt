package com.chs.data.module

import android.app.Application
import androidx.room.Room
import com.chs.data.source.db.AnimeListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideYourListDatabases(app: Application): AnimeListDatabase {
        return Room.databaseBuilder(
            app,
            AnimeListDatabase::class.java,
            "animeList_db"
        ).build()
    }
}