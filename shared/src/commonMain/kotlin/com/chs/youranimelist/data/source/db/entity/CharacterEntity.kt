package com.chs.youranimelist.data.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Entity(tableName = "characterInfo")
data class CharacterEntity @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey
    val id: Int,
    val name: String,
    val nativeName: String,
    val imageUrl: String?,
    val favorite: Int,
    val createDate: Long = Clock.System.now().toEpochMilliseconds()
)
