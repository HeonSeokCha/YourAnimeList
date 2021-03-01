package com.chs.youranimelist.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.MediaSearchQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.SearchRepository
import com.chs.youranimelist.type.MediaType
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    fun getMediaSearch(
        page: Int = 1,
        searchKeyword: String,
        searchType: MediaType
    ): LiveData<NetWorkState<List<MediaSearchQuery.Medium?>>> {
        val responseLiveData: MutableLiveData<NetWorkState<List<MediaSearchQuery.Medium?>>> =
            MutableLiveData()
        viewModelScope.launch {
            repository.getMediaSearch(searchKeyword, searchType).catch { e ->
                responseLiveData.postValue(NetWorkState.Error(e.message.toString()))
            }.collect {
                responseLiveData.postValue(NetWorkState.Success(it.page!!.media!!))
            }
        }
        return responseLiveData
    }

}