package com.chs.youranimelist.data.source.db

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<AnimeListDatabase>
}