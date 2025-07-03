package com.chs.data.module

import androidx.room.Room
import com.chs.data.source.db.AnimeListDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val provideLocalModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AnimeListDatabase::class.java,
            "animeList_db"
        ).build()
    }

    single { get<AnimeListDatabase>().animeListDao }
    single { get<AnimeListDatabase>().tagDao }
    single { get<AnimeListDatabase>().charaListDao }
    single { get<AnimeListDatabase>().searchListDao }
    single { get<AnimeListDatabase>().genreDao }
    single { get<AnimeListDatabase>().tagDao }
}