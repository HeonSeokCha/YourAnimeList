package com.chs.data.module

import android.content.Context
import androidx.room.Room
import com.chs.data.source.db.AnimeListDatabase
import com.chs.data.source.db.dao.AnimeListDao
import com.chs.data.source.db.dao.CharaListDao
import com.chs.data.source.db.dao.GenreDao
import com.chs.data.source.db.dao.SearchListDao
import com.chs.data.source.db.dao.TagDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val LocalModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AnimeListDatabase::class.java,
            "animeList_db"
        ).build()
    }



}

object LocalModulse {

    @Provides
    @Singleton
    fun provideYourListDatabases(@ApplicationContext app: Context): AnimeListDatabase {
        return
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