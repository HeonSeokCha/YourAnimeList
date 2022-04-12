package com.chs.youranimelist.di

import android.app.Application
import androidx.room.Room
import com.chs.youranimelist.data.datasource.YourListDatabase
import com.chs.youranimelist.domain.repository.YourAnimeListRepository
import com.chs.youranimelist.data.repository.YourAnimeListRepositoryImpl
import com.chs.youranimelist.domain.repository.YourCharacterListRepository
import com.chs.youranimelist.data.repository.YourCharacterListRepositoryImpl
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
    fun provideYourListDatabases(app: Application): YourListDatabase {
        return Room.databaseBuilder(
            app,
            YourListDatabase::class.java,
            "yourList_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideYourAnimeListRepository(db: YourListDatabase): YourAnimeListRepository {
        return YourAnimeListRepositoryImpl(db.animeListDao)
    }

    @Provides
    @Singleton
    fun provideYourCharaListRepository(db: YourListDatabase): YourCharacterListRepository {
        return YourCharacterListRepositoryImpl(db.charaListDao)
    }

}