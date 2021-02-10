package com.chs.youranimelist.ui.browse.anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeDetailViewModel(private val repository: AnimeRepository): ViewModel() {
    private val _netWorkState = MutableStateFlow<NetWorkState>(NetWorkState.Empty)
    val netWorkState: StateFlow<NetWorkState> = _netWorkState
    sealed class NetWorkState {
        object Success: NetWorkState()
        data class Error(val message: String): NetWorkState()
        object Loading: NetWorkState()
        object Empty: NetWorkState()
    }
    fun getAnimeInfo(animeId: Input<Int>): LiveData<AnimeDetailQuery.Media> {
        val responseLiveData: MutableLiveData<AnimeDetailQuery.Media> = MutableLiveData()
        _netWorkState.value = NetWorkState.Loading
        viewModelScope.launch {
            repository.getAnimeInfo(animeId).catch { e->
                _netWorkState.value = NetWorkState.Error(e.toString())
            }.collect{
                responseLiveData.value = it.media
                _netWorkState.value = NetWorkState.Success
            }
        }
        return responseLiveData
    }
}