package com.chs.youranimelist.ui.browse.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.browse.studio.StudioAnimeQuery
import com.chs.youranimelist.data.remote.NetWorkState
import com.chs.youranimelist.data.remote.repository.StudioRepository
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudioViewModel @Inject constructor(
    private val repository: StudioRepository
) : ViewModel() {

    private val _studioResponse = SingleLiveEvent<NetWorkState<StudioAnimeQuery.Studio>>()
    val studioResponse: LiveData<NetWorkState<StudioAnimeQuery.Studio>>
        get() = _studioResponse

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