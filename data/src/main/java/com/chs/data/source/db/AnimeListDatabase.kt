package com.chs.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chs.data.RoomConverter
import com.chs.data.source.db.dao.AnimeListDao
import com.chs.data.source.db.dao.CharaListDao
import com.chs.data.source.db.model.AnimeEntity
import com.chs.data.source.db.model.CharacterEntity

@Database(
    entities = [
        AnimeEntity::class,
        CharacterEntity::class
    ], version = 1, exportSchema = false
)
abstract class AnimeListDatabase : RoomDatabase() {
    abstract val animeListDao: AnimeListDao
    abstract val charaListDao: CharaListDao
}