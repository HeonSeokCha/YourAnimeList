package com.chs.youranimelist.presentation.browse.anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.data.model.Anime
import com.chs.youranimelist.domain.usecase.CheckSaveAnimeUseCase
import com.chs.youranimelist.domain.usecase.DeleteAnimeUseCase
import com.chs.youranimelist.domain.usecase.InsertAnimeUseCase
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.usecase.GetAnimeDetailUseCase
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


    fun insertAnimeList(anime: AnimeDetailQuery.Media) {
        viewModelScope.launch(Dispatchers.IO) {
            insertAnimeUseCase(
                Anime(
                    animeId = anime.id,
                    idMal = anime.idMal ?: 0,
                    title = anime.title!!.english ?: anime.title.romaji!!,
                    format = anime.format.toString(),
                    status = anime.status.toString(),
                    season = anime.season.toString(),
                    seasonYear = anime.seasonYear ?: 0,
                    episode = anime.episodes ?: 0,
                    coverImage = anime.coverImage?.extraLarge,
                    bannerImage = anime.bannerImage,
                    averageScore = anime.averageScore ?: 0,
                    favorites = anime.favourites,
                    studio = anime.studios?.edges?.get(0)?.node?.name ?: "",
                    genre = anime.genres ?: listOf()
                )
            )
        }
    }

    fun deleteAnimeList(anime: Anime) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAnimeUseCase(anime)
        }
    }
}

