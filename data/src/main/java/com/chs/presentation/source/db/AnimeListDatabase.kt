package com.chs.presentation.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chs.presentation.source.db.dao.AnimeListDao
import com.chs.presentation.source.db.dao.CharaListDao
import com.chs.presentation.source.db.model.AnimeEntity
import com.chs.presentation.source.db.model.CharacterEntity
import com.chs.presentation.util.RoomConverter

@Database(
    entities = [
        AnimeEntity::class,
        CharacterEntity::class
    ], version = 1, exportSchema = false
)
@TypeConverters(RoomConverter::class)
abstract class AnimeListDatabase : RoomDatabase() {
    abstract val animeListDao: AnimeListDao
    abstract val charaListDao: CharaListDao
}