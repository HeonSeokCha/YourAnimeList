package com.chs.data.source.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.chs.data.source.db.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SearchListDao : BaseDao<SearchHistoryEntity> {
    @Query("SELECT title FROM searchHistory ORDER BY createDate DESC LIMIT 10")
    abstract fun getSearchHistory(): Flow<List<String>>

    @Delete
    abstract suspend fun deleteHistory(historyEntity: SearchHistoryEntity)
}
