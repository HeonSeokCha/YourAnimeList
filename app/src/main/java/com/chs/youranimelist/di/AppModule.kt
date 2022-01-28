package com.chs.youranimelist.di

import android.app.Application
import androidx.room.Room
import com.chs.youranimelist.data.YourListDatabase
import com.chs.youranimelist.data.repository.AnimeListRepository
import com.chs.youranimelist.data.repository.CharacterListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideYourListDatabases(app: Application): YourListDatabase {
        return Room.databaseBuilder(
            app,
            YourListDatabase::class.java,
            "yourList_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAnimeListRepository(db: YourListDatabase): AnimeListRepository {
        return AnimeListRepository(db.yourListDao)
    }


    @Provides
    @Singleton
    fun provideCharaListRepository(db: YourListDatabase): CharacterListRepository {
        return CharacterListRepository(db.yourListDao)
    }
}