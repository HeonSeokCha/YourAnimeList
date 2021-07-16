package com.chs.youranimelist.ui.browse.studio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.StudioAnimeQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.repository.StudioRepository
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.launch

class StudioViewModel(private val repository: StudioRepository) : ViewModel() {

    var page: Int = 1
    var selectsort: MediaSort = MediaSort.START_DATE_DESC
    var studioId: Int = 0
    var hasNextPage: Boolean = true
    var studioAnimeList: ArrayList<StudioAnimeQuery.Edge> = ArrayList()

    val studioResponse by lazy { repository.studioResponse }

    fun getStudio() = viewModelScope.launch {
        repository.getStudioAnime(studioId, selectsort!!, page)
    }

    fun refresh() {
        page = 1
        hasNextPage = true
        studioAnimeList.clear()
        getStudio()
    }
}