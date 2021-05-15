package com.chs.youranimelist.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.chs.youranimelist.data.dto.Character
import com.chs.youranimelist.data.YourListDao
import com.chs.youranimelist.data.YourListDatabase

class CharacterListRepository(application: Application) {
    private val dao: YourListDao by lazy {
        val db = YourListDatabase.getInstance(application)
        db.yourListDao()
    }

    fun getAllCharaList(): LiveData<List<Character>> = dao.getAllCharaList().asLiveData()

    fun checkCharaList(charaId: Int): LiveData<List<Character>> =
        dao.checkCharaList(charaId).asLiveData()

    fun searchCharaList(charaName: String) {
        dao.searchCharaList(charaName)
    }

    suspend fun insertCharaList(character: Character) {
        dao.insertCharaList(character)
    }

    suspend fun deleteCharaList(character: Character) {
        dao.deleteCharaList(character)
    }
}