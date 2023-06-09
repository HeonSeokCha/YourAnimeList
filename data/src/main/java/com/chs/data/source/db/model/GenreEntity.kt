package com.chs.data.source.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "genres"
)
data class GenreEntity(
    @PrimaryKey
    val name: String,
    val symbolColor: String
)
