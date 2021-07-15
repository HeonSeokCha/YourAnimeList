package com.chs.youranimelist.ui.browse.studio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.network.repository.StudioRepository
import kotlinx.coroutines.launch

class StudioViewModel(private val repository: StudioRepository) : ViewModel() {

    val studioResponse by lazy { repository.studioResponse }

    fun getStudio(studioId: Int) = viewModelScope.launch {
        repository.getStudioAnime(studioId)
    }
}