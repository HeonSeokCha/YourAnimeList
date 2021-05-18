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

    private val _charaListResponse = SingleLiveEvent<List<Character>>()
    val charaListResponse: LiveData<List<Character>> get() = _charaListResponse

    suspend fun getAllCharaList() {
        dao.getAllCharaList().collect {
            _charaListResponse.postValue(it)
        }
    }

    fun checkCharaList(charaId: Int): LiveData<List<Character>> =
        dao.checkCharaList(charaId).asLiveData()

    suspend fun searchCharaList(charaName: String) {
        dao.searchCharaList(charaName).collect {
            _charaListResponse.postValue(it)
        }
    }

    suspend fun insertCharaList(character: Character) {
        dao.insertCharaList(character)
    }

    suspend fun deleteCharaList(character: Character) {
        dao.deleteCharaList(character)
    }
}