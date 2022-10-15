package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.youranimelist.AnimeRecommendQuery
import com.chs.youranimelist.domain.usecase.GetAnimeRecUseCase
import com.chs.youranimelist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeRecViewModel @Inject constructor(
    private val getAnimeRecUseCase: GetAnimeRecUseCase
) : ViewModel() {
    var state by mutableStateOf(AnimeRecState())

    fun getAnimeRecommendList(animeId: Int): Flow<PagingData<AnimeRecommendQuery.Edge>> {
        return getAnimeRecUseCase(animeId).cachedIn(viewModelScope)
    }
}