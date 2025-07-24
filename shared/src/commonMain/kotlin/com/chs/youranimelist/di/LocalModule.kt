package com.chs.youranimelist.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.chs.youranimelist.data.source.db.AnimeListDatabase
import com.chs.youranimelist.data.source.db.DatabaseFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val localModule = module {
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    single { get<AnimeListDatabase>().animeListDao }
    single { get<AnimeListDatabase>().tagDao }
    single { get<AnimeListDatabase>().charaListDao }
    single { get<AnimeListDatabase>().searchListDao }
    single { get<AnimeListDatabase>().genreDao }
}
