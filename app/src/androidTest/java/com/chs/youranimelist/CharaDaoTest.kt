package com.chs.youranimelist

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chs.youranimelist.data.model.CharacterDto
import com.chs.youranimelist.data.source.AnimeListDatabase
import com.chs.youranimelist.data.source.CharaListDao
import com.google.common.truth.Truth
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharaDaoTest : TestCase(){

    private lateinit var db: AnimeListDatabase
    private lateinit var charaDao: CharaListDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AnimeListDatabase::class.java).build()
        charaDao = db.charaListDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadCharacter() = runBlocking {
        val chara = CharacterDto(
            charaId = 123456,
            name = "mimiChan",
            nativeName = "mimiChang",
            image = "https://123123",
            favourites = 1231
        )

        charaDao.insertCharaList(chara)
        val charaLists = charaDao.getAllCharaList().first()
        Truth.assertThat(charaLists.contains(chara)).isFalse()
    }
}