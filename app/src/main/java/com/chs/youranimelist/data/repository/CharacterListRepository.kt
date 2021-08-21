package com.chs.youranimelist.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.chs.youranimelist.data.dto.Character
import com.chs.youranimelist.data.YourListDao
import com.chs.youranimelist.data.YourListDatabase
import com.chs.youranimelist.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

class CharacterListRepository(application: Application) {
    private val dao: YourListDao by lazy {
        val db = YourListDatabase.getInstance(application)
        db.yourListDao()
    }

    fun getAllCharaList() = dao.getAllCharaList().flowOn(Dispatchers.IO)

    fun checkCharaList(charaId: Int) = dao.checkCharaList(charaId).flowOn(Dispatchers.IO)

    fun searchCharaList(charaName: String) = dao.searchCharaList(charaName).flowOn(Dispatchers.IO)

    suspend fun insertCharaList(character: Character) {
        dao.insertCharaList(character)
    }

    suspend fun deleteCharaList(character: Character) {
        dao.deleteCharaList(character)
    }
}