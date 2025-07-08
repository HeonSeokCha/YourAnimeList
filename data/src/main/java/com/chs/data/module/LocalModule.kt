package com.chs.data.module

import android.content.Context
import androidx.room.Room
import com.chs.data.source.db.AnimeListDatabase
import com.chs.data.source.db.dao.AnimeListDao
import com.chs.data.source.db.dao.CharaListDao
import com.chs.data.source.db.dao.GenreDao
import com.chs.data.source.db.dao.SearchListDao
import com.chs.data.source.db.dao.TagDao
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class LocalModule {
    @Single
    fun provideRoom(context: Context): AnimeListDatabase {
        return Room.databaseBuilder(
            context,
            AnimeListDatabase::class.java,
            "animeList_db"
        ).build()
    }

    @Factory
    fun provideAnimeListDao(db: AnimeListDatabase): AnimeListDao = db.animeListDao

    @Factory
    fun provideTagDao(db: AnimeListDatabase): TagDao = db.tagDao

    @Factory
    fun provideCharaListDao(db: AnimeListDatabase): CharaListDao = db.charaListDao

    @Factory
    fun provideSearchListDao(db: AnimeListDatabase): SearchListDao = db.searchListDao

    @Factory
    fun provideGenreDao(db: AnimeListDatabase): GenreDao = db.genreDao
}
