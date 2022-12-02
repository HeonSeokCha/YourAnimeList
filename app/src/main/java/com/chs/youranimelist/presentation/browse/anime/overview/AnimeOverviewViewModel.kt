package com.chs.youranimelist.presentation.browse.anime.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.domain.usecase.GetAnimeThemeUseCase
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.data.model.AnimeDetails
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AnimeOverviewViewModel @Inject constructor(
    private val getAnimeOverViewUseCase: AnimeDetailQuery,
    private val getAnimeThemeUseCase: GetAnimeThemeUseCase
) : ViewModel() {

    private val _animeOverviewResponse =
        SingleLiveEvent<NetworkState<AnimeDetailQuery.Data>>()
    val animeOverviewResponse: LiveData<NetworkState<AnimeDetailQuery.Data>>
        get() = _animeOverviewResponse

    private val _animeOverviewThemeResponse = SingleLiveEvent<NetworkState<AnimeDetails?>>()
    val animeOverviewThemeResponse: LiveData<NetworkState<AnimeDetails?>>
        get() = _animeOverviewThemeResponse

    var animeOverviewRelationList = ArrayList<AnimeDetailQuery.RelationsEdge?>()
    var animeDetails: AnimeDetails? = null
    var animeGenresList = ArrayList<String>()
    var animeLinkList = ArrayList<AnimeDetailQuery.ExternalLink?>()
    var animeStudioList = ArrayList<AnimeDetailQuery.StudiosNode>()
    var animeProducerList = ArrayList<AnimeDetailQuery.StudiosNode>()

    fun getAnimeTheme(animeId: Int) {
        viewModelScope.launch {
            getAnimeThemeUseCase(animeId).collect {
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