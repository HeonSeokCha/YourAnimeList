package com.chs.presentation.browse.anime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.presentation.data.mapper.toAnimeDto
import com.chs.presentation.domain.usecase.*
import com.chs.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
    private val checkSaveAnimeUseCase: CheckSaveAnimeUseCase,
    private val insertAnimeUseCase: InsertAnimeUseCase,
    private val deleteAnimeUseCase: DeleteAnimeUseCase,
    private val getAnimeThemeUseCase: GetAnimeThemeUseCase,
) : ViewModel() {

    var state by mutableStateOf(AnimeDetailState())

    fun getAnimeDetailInfo(id: Int) {
        viewModelScope.launch {
            getAnimeDetailUseCase(id).collect {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            animeDetailInfo = it.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    fun getAnimeTheme(idMal: Int) {
        viewModelScope.launch {
            getAnimeThemeUseCase(idMal).collect {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            animeThemes = it.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    fun isSaveAnime(animeId: Int) {
        viewModelScope.launch {
            checkSaveAnimeUseCase(animeId).collect {
                if (it != null && it.animeId == animeId) {
                    state = state.copy(
                        isSaveAnime = it
                    )
                }
            }
        }
    }

    fun insertAnime() {
        val anime = state.animeDetailInfo?.media!!
        viewModelScope.launch {
            val animeObj = com.chs.domain.model.Anime(
                animeId = anime.id,
                idMal = anime.idMal ?: 0,
                title = anime.title!!.english ?: anime.title.romaji!!,
                format = anime.format.toString(),
                seasonYear = anime.seasonYear ?: 0,
                episode = anime.episodes ?: 0,
                coverImage = anime.coverImage?.extraLarge,
                averageScore = anime.averageScore ?: 0,
                favorites = anime.favourites,
                studio = if (!anime.studios?.studiosEdges.isNullOrEmpty()) anime.studios?.studiosEdges?.get(
                    0
                )?.studiosNode?.name else "",
                genre = anime.genres ?: listOf()
            )
            insertAnimeUseCase(animeObj)
            state = state.copy(isSaveAnime = animeObj.toAnimeDto())
        }
    }

    fun deleteAnime(anime: AnimeDto) {
        viewModelScope.launch {
            deleteAnimeUseCase(anime)
            state = state.copy(isSaveAnime = null)
        }
    }
}