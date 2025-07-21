package com.chs.data.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tagInfo")
data class TagEntity(
    @PrimaryKey
    val name: String,
    val desc: String?
)
