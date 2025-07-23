package com.chs.youranimelist.data.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey
    val name: String
)
