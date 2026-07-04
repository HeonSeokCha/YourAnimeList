package com.chs.youranimelist.data.source.db.dao

import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Upsert

interface BaseDao<T> {

    @Upsert
    suspend fun insert(entity: T)

    @Upsert
    suspend fun insertMultiple(vararg entity: T)

    @Delete
    suspend fun delete(entity: T)
}