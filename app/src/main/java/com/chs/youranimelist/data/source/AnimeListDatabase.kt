package com.chs.youranimelist.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.data.model.CharacterDto
import com.chs.youranimelist.util.RoomConverter

@Database(
    entities = [
        AnimeDto::class,
        CharacterDto::class
    ], version = 1, exportSchema = false
)
@TypeConverters(RoomConverter::class)
abstract class AnimeListDatabase : RoomDatabase() {
    abstract val animeListDao: AnimeListDao
    abstract val charaListDao: CharaListDao
}