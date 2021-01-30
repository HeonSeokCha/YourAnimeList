package com.chs.youranimelist.ui.detail.anime

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.network.repository.AnimeRepository

class AnimeDetailViewModel(private val repository: AnimeRepository): ViewModel() {
    var animeId: Int? = null
    var currentAnimeData: AnimeDetailQuery.Media? = null

    fun getAnime() {
        if(animeId != null) repository.getAnimeInfo(Input.fromNullable(animeId!!))
    }

}