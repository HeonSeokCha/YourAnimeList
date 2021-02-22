package com.chs.youranimelist.ui.browse.anime.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeRecommendQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeRecommendViewModel(private val repository: AnimeRepository) : ViewModel() {

    fun getRecommendList(animeId: Int): LiveData<NetWorkState<List<AnimeRecommendQuery.Edge?>>> {
        val responseLiveData: MutableLiveData<NetWorkState<List<AnimeRecommendQuery.Edge?>>> =
            MutableLiveData()
        viewModelScope.launch {
            repository.getAnimeRecList(animeId.toInput()).catch { e ->
                responseLiveData.postValue(NetWorkState.Error(e.message!!))
            }.collect {
                responseLiveData.postValue(NetWorkState.Success(it.media!!.recommendations!!.edges!!))
            }
        }
        return responseLiveData
    }
}