package com.chs.youranimelist.data.source.db.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.chs.youranimelist.data.source.db.entity.GenreEntity

@Dao
abstract class GenreDao : BaseDao<GenreEntity> {

    @Query("SELECT * FROM genres")
    abstract suspend fun getAllGenres(): List<GenreEntity>
}