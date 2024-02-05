package com.chs.data.source.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Upsert

interface BaseDao<T> {

    @Upsert
    suspend fun insert(entity: T)

    @Upsert
    suspend fun insertMultiple(vararg entity: T)

    @Delete
    suspend fun delete(entity: T)
}