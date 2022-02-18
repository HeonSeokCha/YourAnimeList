package com.chs.youranimelist.ui.browse.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.browse.studio.StudioAnimeQuery
import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.repository.StudioRepository
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudioViewModel @Inject constructor(
    private val repositoryImpl: StudioRepository
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
        _studioResponse.value = NetworkState.Loading()
        viewModelScope.launch {
            repositoryImpl.getStudioAnime(studioId, selectsort!!, page).catch { e ->
                _studioResponse.value = NetworkState.Error(e.message.toString())
            }.collect {
                if (it.data?.studio == null) {
                    _studioResponse.value = NetworkState.Error("Not Found")
                } else {
                    _studioResponse.value = NetworkState.Success(it.data!!.studio!!)
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