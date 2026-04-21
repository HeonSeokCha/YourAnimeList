package com.chs.youranimelist.data.source.db

import androidx.room.Room
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


@OptIn(ExperimentalForeignApi::class)
fun getDatabaseBuilder(): AnimeListDatabase {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    ).run { requireNotNull(this?.path())}

    val dbFile = "$documentDirectory/${AnimeListDatabase.DB_NAME}"
    return Room.databaseBuilder<AnimeListDatabase>(
        name = dbFile
    )
        .setDriver(androidx.sqlite.driver.bundled.BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}