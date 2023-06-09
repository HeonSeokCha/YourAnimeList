package com.chs.data.module

import android.content.Context
import androidx.room.Room
import com.chs.data.source.db.AnimeListDatabase
import com.chs.data.source.db.dao.AnimeListDao
import com.chs.data.source.db.dao.CharaListDao
import com.chs.data.source.db.dao.GenreDao
import com.chs.data.source.db.dao.SearchListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideYourListDatabases(@ApplicationContext app: Context): AnimeListDatabase {
        return Room.databaseBuilder(
            app,
            AnimeListDatabase::class.java,
            "animeList_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAnimeDao(db: AnimeListDatabase): AnimeListDao {
        return db.animeListDao
    }

    @Provides
    @Singleton
    fun provideCharaDao(db: AnimeListDatabase): CharaListDao {
        return db.charaListDao
    }

    @Provides
    @Singleton
    fun provideSearchDao(db: AnimeListDatabase): SearchListDao {
        return db.searchListDao
    }

    @Provides
    @Singleton
    fun provideGenreDao(db: AnimeListDatabase): GenreDao {
        return db.genreDao
    }
}