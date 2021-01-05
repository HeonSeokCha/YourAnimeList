package com.chs.youranimelist.viewmodel

import androidx.lifecycle.*
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.ViewPagerQuery
import com.chs.youranimelist.network.repository.AnimeListRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository:AnimeListRepository): ViewModel() {
    private val _netWorkState = MutableStateFlow<NetWorkState>(NetWorkState.Empty)
    val netWorkState: StateFlow<NetWorkState> = _netWorkState


    sealed class NetWorkState {
        object Success: NetWorkState()
        data class Error(val message: String): NetWorkState()
        object Loading: NetWorkState()
        object Empty: NetWorkState()
    }

    fun getPagerAnimeList(): LiveData<ViewPagerQuery.Page> {
        val responseLiveData: MutableLiveData<ViewPagerQuery.Page> = MutableLiveData()
        _netWorkState.value = NetWorkState.Loading
        viewModelScope.launch {
            repository.getAnimeViewPager().catch { e->
                _netWorkState.value = NetWorkState.Error(e.toString())
            }.collect{
                responseLiveData.value = it.page
                _netWorkState.value = NetWorkState.Success
            }
        }
        return responseLiveData
    }

    fun getAnimeInfo(animeId: Input<Int>):LiveData<AnimeDetailQuery.Media> {
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