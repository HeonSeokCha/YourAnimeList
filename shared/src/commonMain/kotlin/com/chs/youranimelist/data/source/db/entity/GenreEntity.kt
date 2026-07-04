package com.chs.youranimelist.data.source.db.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey
    val name: String
)
