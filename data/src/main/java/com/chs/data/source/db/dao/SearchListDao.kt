package com.chs.data.source.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.chs.data.source.db.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SearchListDao : BaseDao<SearchHistoryEntity> {
    @Query("SELECT title FROM searchHistory ORDER BY createDate DESC LIMIT 10")
    abstract fun getSearchHistory(): Flow<List<String>>
}
