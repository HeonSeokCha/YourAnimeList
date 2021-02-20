package com.chs.youranimelist.ui.browse.anime.recommend

import androidx.lifecycle.ViewModel
import com.chs.youranimelist.network.repository.AnimeRepository

class AnimeRecommendViewModel(private val repository: AnimeRepository) : ViewModel() {

    fun getRecommendList() {
        repository
    }
}