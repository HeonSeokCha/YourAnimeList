package com.chs.youranimelist.ui.browse.anime.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeRecommendQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeRecommendViewModel(private val repository: AnimeRepository) : ViewModel() {

    private val _animeRecUiState: MutableStateFlow<NetWorkState<List<AnimeRecommendQuery.Edge?>>> =
        MutableStateFlow(NetWorkState.Loading())
    val animeRecUiState = _animeRecUiState.asStateFlow()

    fun getRecommendList(animeId: Int) {
        viewModelScope.launch {
            _animeRecUiState.value = NetWorkState.Loading()
            repository.getAnimeRecList(animeId.toInput()).catch { e ->
                _animeRecUiState.value = NetWorkState.Error(e.message!!)
            }.collect {
                _animeRecUiState.value = NetWorkState.Success(it.media!!.recommendations!!.edges!!)
            }
        }
    }
}