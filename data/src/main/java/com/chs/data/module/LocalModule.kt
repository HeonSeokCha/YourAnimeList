package com.chs.data.module

import android.content.Context
import androidx.room.Room
import com.chs.data.source.db.AnimeListDatabase
import com.chs.data.source.db.dao.AnimeListDao
import com.chs.data.source.db.dao.CharaListDao
import com.chs.data.source.db.dao.GenreDao
import com.chs.data.source.db.dao.SearchListDao
import com.chs.data.source.db.dao.TagDao
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
    fun provideAnimeDao(db: AnimeListDatabase): AnimeListDao {
        return db.animeListDao
    }

    @Provides
    fun provideCharaDao(db: AnimeListDatabase): CharaListDao {
        return db.charaListDao
    }

    @Provides
    fun provideSearchDao(db: AnimeListDatabase): SearchListDao {
        return db.searchListDao
    }

    @Provides
    fun provideGenreDao(db: AnimeListDatabase): GenreDao {
        return db.genreDao
    }

    @Provides
    fun provideTagDao(db: AnimeListDatabase): TagDao {
        return db.tagDao
    }
}