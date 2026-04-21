package com.chs.youranimelist.data.source.db

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

fun getDatabaseBuilder(context: Context): AnimeListDatabase {
    val dbFile = context.getDatabasePath(AnimeListDatabase.DB_NAME)
    return Room.databaseBuilder<AnimeListDatabase>(context, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}