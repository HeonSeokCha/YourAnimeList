package com.chs.youranimelist.data.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Entity("searchHistory")
data class SearchHistoryEntity @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey
    val title: String,
    val createDate: Long = Clock.System.now().toEpochMilliseconds()
)