package com.chs.data.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("searchHistory")
data class SearchHistoryEntity(
    @PrimaryKey
    val title: String,
    val createDate: Long = System.currentTimeMillis()
)