package com.chs.youranimelist.ui.browse.anime.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeOverviewQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.network.response.AnimeDetails
import com.chs.youranimelist.util.SingleLiveEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class AnimeOverviewViewModel : ViewModel() {

    private val _animeOverviewResponse = SingleLiveEvent<NetWorkState<AnimeOverviewQuery.Data>>()
    val animeOverviewResponse: LiveData<NetWorkState<AnimeOverviewQuery.Data>>
        get() = _animeOverviewResponse

    private val _animeOverviewThemeResponse = SingleLiveEvent<AnimeDetails?>()
    val animeOverviewThemeResponse: LiveData<AnimeDetails?>
        get() = _animeOverviewThemeResponse

    private val repository by lazy { AnimeRepository() }

    var animeOverviewRelationList = ArrayList<AnimeOverviewQuery.RelationsEdge?>()
    var animeDetails: AnimeDetails? = null
    var animeGenresList = ArrayList<String>()
    var animeLinkList = ArrayList<AnimeOverviewQuery.ExternalLink?>()
    var animeStudioList = ArrayList<AnimeOverviewQuery.StudiosNode>()
    var animeProducerList = ArrayList<AnimeOverviewQuery.StudiosNode>()

    fun getAnimeOverview(animeId: Int) {
        viewModelScope.launch {
            _animeOverviewResponse.value = NetWorkState.Loading()
            repository.getAnimeOverview(animeId.toInput()).catch { e ->
                _animeOverviewResponse.value = NetWorkState.Error(e.message.toString())
            }.collect {
                _animeOverviewResponse.value = NetWorkState.Success(it.data!!)
            }
        }
    }

    fun getAnimeTheme(animeId: Int) {
        viewModelScope.launch {
            repository.getAnimeOverviewTheme(animeId).apply {
                if (this != null) {
                    _animeOverviewThemeResponse.value = this
                } else {
                    _animeOverviewThemeResponse.value = null
                }
            }
        }
    }

    fun clearList() {
        animeDetails = null
        animeOverviewRelationList.clear()
        animeGenresList.clear()
        animeLinkList.clear()
        animeStudioList.clear()
        animeProducerList.clear()
    }
}