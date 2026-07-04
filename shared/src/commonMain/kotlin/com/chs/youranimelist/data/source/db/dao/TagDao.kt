package com.chs.youranimelist.data.source.db.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.chs.youranimelist.data.source.db.entity.TagEntity

@Dao
abstract class TagDao : BaseDao<TagEntity> {

    @Query("SELECT * FROM tagInfo")
    abstract suspend fun getAllTags(): List<TagEntity>
}