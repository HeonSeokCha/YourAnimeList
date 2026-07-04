package com.chs.youranimelist.data.source.db.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity("tagInfo")
data class TagEntity(
    @PrimaryKey
    val name: String,
    val desc: String?
)
