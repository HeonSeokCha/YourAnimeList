package com.chs.presentation.browse.anime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeRecViewModel @Inject constructor(

) : ViewModel() {

    var state by mutableStateOf(AnimeRecState())

    fun getAnimeRecommendList(animeId: Int) {
    }
}