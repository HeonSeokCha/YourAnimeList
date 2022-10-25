package com.chs.youranimelist

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.data.source.AnimeListDao
import com.chs.youranimelist.data.source.AnimeListDatabase
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class AnimeDaoTest : TestCase() {

    private lateinit var db: AnimeListDatabase
    private lateinit var animeDao: AnimeListDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AnimeListDatabase::class.java).build()
        animeDao = db.animeListDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadAnime() = runBlocking {
        val anime = AnimeDto(
            animeId = 123456,
            idMal = 987654,
            title = "Chs Fantasy",
            format = "TV",
            seasonYear = 2022,
            episode = 12,
            coverImage = "https://123123",
            averageScore = 80,
            favorites = 99,
            genre = listOf("Action", "Horror")
        )

        animeDao.insertAnimeList(anime)
        val animeLists = animeDao.getAllAnimeList().first()
        assertThat(animeLists.contains(anime)).isFalse()
    }
}