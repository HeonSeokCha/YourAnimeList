package com.chs.youranimelist.ui.search.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.SearchRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchCharacterViewModel(private val repository: SearchRepository) : ViewModel() {

    fun getCharacterSearch(
        page: Int = 1,
        searchKeyword: String,
    ): LiveData<NetWorkState<List<SearchCharacterQuery.Character?>>> {
        val responseLiveData: MutableLiveData<NetWorkState<List<SearchCharacterQuery.Character?>>> =
            MutableLiveData()
        viewModelScope.launch {
            repository.getCharacterSearch(searchKeyword).catch { e ->
                responseLiveData.postValue(NetWorkState.Error(e.message.toString()))
            }.collect {
                responseLiveData.postValue(NetWorkState.Success(it.page!!.characters!!))
            }
        }
        return responseLiveData
    }
}