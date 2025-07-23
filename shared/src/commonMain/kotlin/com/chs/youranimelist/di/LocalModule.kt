package com.chs.youranimelist.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.chs.youranimelist.data.source.db.dao.AnimeListDao
import com.chs.youranimelist.data.source.db.dao.CharaListDao
import com.chs.youranimelist.data.source.db.dao.GenreDao
import com.chs.youranimelist.data.source.db.dao.SearchListDao
import com.chs.youranimelist.data.source.db.dao.TagDao
import com.chs.youranimelist.data.source.db.AnimeListDatabase
import com.chs.youranimelist.data.source.db.DatabaseFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class LocalModule {
    @Single
    fun provideRoom(factory: DatabaseFactory): AnimeListDatabase {
        return factory
            .create()
            .setQueryCoroutineContext(Dispatchers.IO)
            .setDriver(BundledSQLiteDriver())
            .build()
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
