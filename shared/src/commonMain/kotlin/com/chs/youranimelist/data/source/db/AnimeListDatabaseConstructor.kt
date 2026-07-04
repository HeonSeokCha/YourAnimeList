package com.chs.youranimelist.data.source.db

import androidx.room3.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AnimeListDatabaseConstructor : RoomDatabaseConstructor<AnimeListDatabase> {
    override fun initialize(): AnimeListDatabase
}