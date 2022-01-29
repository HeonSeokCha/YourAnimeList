package com.chs.youranimelist.ui.browse.anime.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.browse.anime.AnimeOverviewQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.network.response.AnimeDetails
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AnimeOverviewViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _animeOverviewResponse =
        SingleLiveEvent<NetWorkState<AnimeOverviewQuery.Data>>()
    val animeOverviewResponse: LiveData<NetWorkState<AnimeOverviewQuery.Data>>
        get() = _animeOverviewResponse

    private val _animeOverviewThemeResponse = SingleLiveEvent<AnimeDetails?>()
    val animeOverviewThemeResponse: LiveData<AnimeDetails?>
        get() = _animeOverviewThemeResponse

    var animeOverviewRelationList = ArrayList<AnimeOverviewQuery.RelationsEdge?>()
    var animeDetails: AnimeDetails? = null
    var animeGenresList = ArrayList<String>()
    var animeLinkList = ArrayList<AnimeOverviewQuery.ExternalLink?>()
    var animeStudioList = ArrayList<AnimeOverviewQuery.StudiosNode>()
    var animeProducerList = ArrayList<AnimeOverviewQuery.StudiosNode>()

    fun getAnimeOverview(animeId: Int) {
        _animeOverviewResponse.value = NetWorkState.Loading()
        viewModelScope.launch {
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