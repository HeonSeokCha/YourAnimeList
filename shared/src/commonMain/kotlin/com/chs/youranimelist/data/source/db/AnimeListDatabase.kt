package com.chs.youranimelist.data.source.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.chs.data.source.db.dao.AnimeListDao
import com.chs.data.source.db.dao.CharaListDao
import com.chs.data.source.db.dao.GenreDao
import com.chs.data.source.db.dao.SearchListDao
import com.chs.data.source.db.dao.TagDao
import com.chs.data.source.db.entity.AnimeEntity
import com.chs.data.source.db.entity.CharacterEntity
import com.chs.data.source.db.entity.GenreEntity
import com.chs.data.source.db.entity.SearchHistoryEntity
import com.chs.data.source.db.entity.TagEntity

@Database(
    entities = [
        AnimeEntity::class,
        CharacterEntity::class,
        SearchHistoryEntity::class,
        GenreEntity::class,
        TagEntity::class
    ], version = 1, exportSchema = false
)
@ConstructedBy(AnimeListDatabaseConstructor::class)
abstract class AnimeListDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME: String = "animeList.db"
    }

    abstract val animeListDao: AnimeListDao
    abstract val charaListDao: CharaListDao
    abstract val searchListDao: SearchListDao
    abstract val genreDao: GenreDao
    abstract val tagDao: TagDao
}