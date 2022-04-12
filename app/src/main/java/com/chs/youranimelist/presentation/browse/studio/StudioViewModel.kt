package com.chs.youranimelist.presentation.browse.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.browse.studio.StudioAnimeQuery
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.usecase.GetStudioUseCase
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudioViewModel @Inject constructor(
    private val useCase: GetStudioUseCase
) : ViewModel() {

    private val _studioResponse = SingleLiveEvent<NetworkState<StudioAnimeQuery.Studio>>()
    val studioResponse: LiveData<NetworkState<StudioAnimeQuery.Studio>>
        get() = _studioResponse

    var page: Int = 1
    var selectsort: MediaSort = MediaSort.START_DATE_DESC
    var studioId: Int = 0
    var hasNextPage: Boolean = true
    var studioAnimeList: ArrayList<StudioAnimeQuery.Edge?> = arrayListOf()

    fun getStudioAnime() {
        viewModelScope.launch {
            useCase.invoke(
                studioId,
                selectsort,
                page
            ).collect {
                _studioResponse.value = it
            }
        }
    }

    fun refresh() {
        page = 1
        hasNextPage = true
        studioAnimeList.clear()
        getStudioAnime()
    }
}