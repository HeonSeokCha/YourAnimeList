package com.chs.youranimelist.data.source.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<AnimeListDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(AnimeListDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}