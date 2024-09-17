package com.chs.data.source.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.chs.data.source.db.entity.TagEntity

@Dao
abstract class TagDao : BaseDao<TagEntity> {

    @Query("SELECT * FROM tagInfo")
    abstract suspend fun getAllTags(): List<TagEntity>
}