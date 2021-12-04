package com.chs.youranimelist.ui.browse.studio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.browse.studio.StudioAnimeQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.StudioRepository
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StudioViewModel : ViewModel() {

    private val _studioResponse =
        MutableStateFlow<NetWorkState<StudioAnimeQuery.Studio>>(NetWorkState.Loading())
    val studioResponse: StateFlow<NetWorkState<StudioAnimeQuery.Studio>>
        get() = _studioResponse

    private val repository by lazy { StudioRepository() }

    var page: Int = 1
    var selectsort: MediaSort = MediaSort.START_DATE_DESC
    var studioId: Int = 0
    var hasNextPage: Boolean = true

    var studioAnimeList: ArrayList<StudioAnimeQuery.Edge?> = arrayListOf()

    fun getStudioAnime() {
        _studioResponse.value = NetWorkState.Loading()
        viewModelScope.launch {
            repository.getStudioAnime(studioId, selectsort!!, page).catch { e ->
                _studioResponse.value = NetWorkState.Error(e.message.toString())
            }.collect {
                if (it.data?.studio == null) {
                    _studioResponse.value = NetWorkState.Error("Not Found")
                } else {
                    _studioResponse.value = NetWorkState.Success(it.data!!.studio!!)
                }
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