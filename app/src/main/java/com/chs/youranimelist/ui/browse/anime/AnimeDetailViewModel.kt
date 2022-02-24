package com.chs.youranimelist.ui.browse.anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Input
import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.data.domain.model.Anime
import com.chs.youranimelist.data.domain.usecase.CheckSaveAnimeUseCase
import com.chs.youranimelist.data.domain.usecase.DeleteAnimeUseCase
import com.chs.youranimelist.data.domain.usecase.InsertAnimeUseCase
import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.usecase.GetAnimeDetailUseCase
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val checkSaveAnimeUseCase: CheckSaveAnimeUseCase,
    private val insertAnimeUseCase: InsertAnimeUseCase,
    private val deleteAnimeUseCase: DeleteAnimeUseCase,
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
) : ViewModel() {

    private val _animeDetailResponse = SingleLiveEvent<NetworkState<AnimeDetailQuery.Data>>()
    val animeDetailResponse: LiveData<NetworkState<AnimeDetailQuery.Data>>
        get() = _animeDetailResponse


    var animeDetail: AnimeDetailQuery.Media? = null
    var initAnimeList: Anime? = null

    fun getAnimeDetail(animeId: Int) {
        viewModelScope.launch {
            getAnimeDetailUseCase(animeId).collect {
                _animeDetailResponse.value = it
            }
        }
    }

    fun checkAnimeList(animeId: Int): LiveData<Anime?> =
        checkSaveAnimeUseCase(animeId).asLiveData()


    fun insertAnimeList(anime: Anime) {
        viewModelScope.launch(Dispatchers.IO) {
            insertAnimeUseCase(anime)
        }
    }

    fun deleteAnimeList(anime: Anime) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAnimeUseCase(anime)
        }
    }
}

