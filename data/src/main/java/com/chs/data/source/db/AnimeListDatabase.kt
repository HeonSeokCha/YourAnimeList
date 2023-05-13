package com.chs.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chs.data.source.db.dao.AnimeListDao
import com.chs.data.source.db.dao.CharaListDao
import com.chs.data.source.db.dao.SearchListDao
import com.chs.data.source.db.model.AnimeEntity
import com.chs.data.source.db.model.CharacterEntity
import com.chs.data.source.db.model.SearchHistoryEntity

@Database(
    entities = [
        AnimeEntity::class,
        CharacterEntity::class,
        SearchHistoryEntity::class
    ], version = 1, exportSchema = false
)
abstract class AnimeListDatabase : RoomDatabase() {
    abstract val animeListDao: AnimeListDao
    abstract val charaListDao: CharaListDao
    abstract val searchListDao: SearchListDao
}