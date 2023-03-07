package com.chs.youranimelist.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chs.youranimelist.source.db.dao.AnimeListDao
import com.chs.youranimelist.source.db.dao.CharaListDao
import com.chs.youranimelist.source.db.model.AnimeEntity
import com.chs.youranimelist.source.db.model.CharacterEntity
import com.chs.youranimelist.util.RoomConverter

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