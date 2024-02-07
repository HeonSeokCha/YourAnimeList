package com.chs.data.source.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.chs.data.source.db.entity.GenreEntity

@Dao
abstract class GenreDao : BaseDao<GenreEntity> {

    @Query("SELECT * FROM genres")
    abstract suspend fun getAllGenres(): List<GenreEntity>
}