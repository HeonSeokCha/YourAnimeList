package com.chs.youranimelist.ui.search.anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.SearchRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchAnimeViewModel(private val repository: SearchRepository) : ViewModel() {

    fun getAnimeSearch(
        page: Int = 1,
        searchKeyword: String,
    ): LiveData<NetWorkState<List<SearchAnimeQuery.Medium?>>> {
        val responseLiveData: MutableLiveData<NetWorkState<List<SearchAnimeQuery.Medium?>>> =
            MutableLiveData()
        viewModelScope.launch {
            repository.getAnimeSearch(searchKeyword).catch { e ->
                responseLiveData.postValue(NetWorkState.Error(e.message.toString()))
            }.collect {
                responseLiveData.postValue(NetWorkState.Success(it.page!!.media!!))
            }
        }
        return responseLiveData
    }
}