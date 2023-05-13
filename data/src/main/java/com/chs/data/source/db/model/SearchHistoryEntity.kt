package com.chs.data.source.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("searchHistory")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val createDate: Long = System.currentTimeMillis(),
    val title: String,
)
