package com.chs.youranimelist.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.chs.youranimelist.data.dto.Character
import com.chs.youranimelist.data.YourListDao
import com.chs.youranimelist.data.YourListDatabase
import com.chs.youranimelist.util.SingleLiveEvent
import kotlinx.coroutines.flow.collect

class CharacterListRepository(application: Application) {
    private val dao: YourListDao by lazy {
        val db = YourListDatabase.getInstance(application)
        db.yourListDao()
    }

    fun getAllCharaList() = dao.getAllCharaList()

    fun checkCharaList(charaId: Int) = dao.checkCharaList(charaId)

    fun searchCharaList(charaName: String) = dao.searchCharaList(charaName)

    suspend fun insertCharaList(character: Character) {
        dao.insertCharaList(character)
    }

    suspend fun deleteCharaList(character: Character) {
        dao.deleteCharaList(character)
    }
}