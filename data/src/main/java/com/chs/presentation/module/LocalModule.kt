package com.chs.presentation.module

import android.app.Application
import androidx.room.Room
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
    fun provideYourListDatabases(app: Application): com.chs.presentation.source.db.AnimeListDatabase {
        return Room.databaseBuilder(
            app,
            com.chs.presentation.source.db.AnimeListDatabase::class.java,
            "animeList_db"
        ).build()
    }
}