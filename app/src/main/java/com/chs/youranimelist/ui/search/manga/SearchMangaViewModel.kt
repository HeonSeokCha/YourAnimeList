package com.chs.youranimelist.ui.search.manga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.SearchRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchMangaViewModel(private val repository: SearchRepository) : ViewModel() {

    fun getMangaSearch(
        page: Int = 1,
        searchKeyword: String,
    ): LiveData<NetWorkState<List<SearchMangaQuery.Medium?>>> {
        val responseLiveData: MutableLiveData<NetWorkState<List<SearchMangaQuery.Medium?>>> =
            MutableLiveData()
        viewModelScope.launch {
            repository.getMangaSearch(searchKeyword).catch { e ->
                responseLiveData.postValue(NetWorkState.Error(e.message.toString()))
            }.collect {
                responseLiveData.postValue(NetWorkState.Success(it.page!!.media!!))
            }
        }
        return responseLiveData
    }
}