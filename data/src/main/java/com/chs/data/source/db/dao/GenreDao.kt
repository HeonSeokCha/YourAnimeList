package com.chs.data.source.db.dao

import androidx.room.Query
import com.chs.data.source.db.model.GenreEntity

abstract class GenreDao : BaseDao<GenreEntity> {

    @Query("SELECT * FROM genres")
    abstract suspend fun getAllGenres(): List<GenreEntity>
}