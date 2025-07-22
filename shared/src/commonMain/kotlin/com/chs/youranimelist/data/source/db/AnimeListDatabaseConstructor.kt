package com.chs.youranimelist.data.source.db

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AnimeListDatabaseConstructor : RoomDatabaseConstructor<AnimeListDatabase> {
    override fun initialize(): AnimeListDatabase
}