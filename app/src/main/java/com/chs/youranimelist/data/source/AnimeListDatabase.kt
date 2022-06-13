package com.chs.youranimelist.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chs.youranimelist.data.model.Anime

@Database(
    entities = [
        Anime::class,
        Character::class
    ], version = 1, exportSchema = false
)
abstract class AnimeListDatabase : RoomDatabase() {
    abstract val animeListDao: AnimeListDao
    abstract val charaListDao: CharaListDao
}