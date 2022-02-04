package com.chs.youranimelist.data.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chs.youranimelist.data.domain.model.Anime
import com.chs.youranimelist.data.domain.model.Character


@Database(
    entities = [
        Anime::class,
        Character::class
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class YourListDatabase : RoomDatabase() {
    abstract val animeListDao: AnimeListDao
    abstract val charaListDao: CharaListDao
}