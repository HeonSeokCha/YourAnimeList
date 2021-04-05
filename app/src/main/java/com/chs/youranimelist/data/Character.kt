package com.chs.youranimelist.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "character")
@Parcelize
data class Character(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val charaId: Int,
    val name: String? = null,
    val nativeName: String? = null,
    val image: String? = null,
    val favourites: Int? = null,
    val created: String = ""
) : Parcelable
