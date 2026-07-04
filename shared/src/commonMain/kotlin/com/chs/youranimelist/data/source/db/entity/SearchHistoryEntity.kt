package com.chs.youranimelist.data.source.db.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Entity("searchHistory")
data class SearchHistoryEntity @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey
    val title: String,
    val createDate: Long = Clock.System.now().toEpochMilliseconds()
)