package com.chs.domain.repository

import kotlinx.coroutines.flow.Flow

interface BaseMediaRepository<T> {

    suspend fun insertMediaInfo(info: T)

    suspend fun deleteMediaInfo(info: T)

    fun getSavedMediaInfo(id: Int): Flow<T?>

    fun getSavedMediaInfoList(): Flow<List<T>>
}