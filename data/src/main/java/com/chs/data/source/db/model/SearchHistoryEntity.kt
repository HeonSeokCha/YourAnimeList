package com.chs.data.source.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("searchHistory")
data class SearchHistoryEntity(
    @PrimaryKey
    val title: String,
    val createDate: Long = System.currentTimeMillis()
)
