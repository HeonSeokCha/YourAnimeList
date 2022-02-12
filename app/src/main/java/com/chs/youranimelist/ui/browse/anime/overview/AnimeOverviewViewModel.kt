package com.chs.youranimelist.ui.browse.anime.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.browse.anime.AnimeOverviewQuery
import com.chs.youranimelist.data.remote.usecase.GetAnimeThemeUseCase
import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.dto.AnimeDetails
import com.chs.youranimelist.data.remote.usecase.GetAnimeOverViewUseCase
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AnimeOverviewViewModel @Inject constructor(
    private val getAnimeOverViewUseCase: GetAnimeOverViewUseCase,
    private val getAnimeThemeUseCase: GetAnimeThemeUseCase
) : ViewModel() {

    private val _animeOverviewResponse =
        SingleLiveEvent<NetworkState<AnimeOverviewQuery.Data>>()
    val animeOverviewResponse: LiveData<NetworkState<AnimeOverviewQuery.Data>>
        get() = _animeOverviewResponse

    private val _animeOverviewThemeResponse = SingleLiveEvent<NetworkState<AnimeDetails?>>()
    val animeOverviewThemeResponse: LiveData<NetworkState<AnimeDetails?>>
        get() = _animeOverviewThemeResponse

    var animeOverviewRelationList = ArrayList<AnimeOverviewQuery.RelationsEdge?>()
    var animeDetails: AnimeDetails? = null
    var animeGenresList = ArrayList<String>()
    var animeLinkList = ArrayList<AnimeOverviewQuery.ExternalLink?>()
    var animeStudioList = ArrayList<AnimeOverviewQuery.StudiosNode>()
    var animeProducerList = ArrayList<AnimeOverviewQuery.StudiosNode>()

    fun getAnimeOverview(animeId: Int) {
        viewModelScope.launch {
            getAnimeOverViewUseCase(animeId.toInput()).collect {
                _animeOverviewResponse.value = it
            }
        }
    }

    fun getAnimeTheme(animeId: Int) {
        viewModelScope.launch {
            getAnimeThemeUseCase.invoke(animeId).collect {
                _animeOverviewThemeResponse.value = it
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