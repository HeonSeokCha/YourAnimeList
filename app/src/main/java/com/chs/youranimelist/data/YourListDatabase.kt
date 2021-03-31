package com.chs.youranimelist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [
        Anime::class,
        Character::class
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class YourListDatabase : RoomDatabase() {

    abstract fun yourListDao(): YourListDao

    companion object {
        @Volatile
        private var INSTANCE: YourListDatabase? = null

        fun getInstance(context: Context): YourListDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        YourListDatabase::class.java, "yourList_db"
                    ).build()
                }
                return instance
            }
        }
    }
}