package com.chs.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chs.data.source.db.dao.AnimeListDao
import com.chs.data.source.db.dao.CharaListDao
import com.chs.data.source.db.dao.GenreDao
import com.chs.data.source.db.dao.SearchListDao
import com.chs.data.source.db.entity.AnimeEntity
import com.chs.data.source.db.entity.CharacterEntity
import com.chs.data.source.db.entity.GenreEntity
import com.chs.data.source.db.entity.SearchHistoryEntity

@Database(
    entities = [
        AnimeEntity::class,
        CharacterEntity::class,
        SearchHistoryEntity::class,
        GenreEntity::class
    ], version = 2, exportSchema = false
)
abstract class AnimeListDatabase : RoomDatabase() {
    abstract val animeListDao: AnimeListDao
    abstract val charaListDao: CharaListDao
    abstract val searchListDao: SearchListDao
    abstract val genreDao: GenreDao
}