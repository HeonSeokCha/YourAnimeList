package com.chs.youranimelist.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    private val _animeSearchUiState: MutableStateFlow<NetWorkState<List<SearchAnimeQuery.Medium?>>> =
        MutableStateFlow(NetWorkState.Loading())
    val animeSearchUiState = _animeSearchUiState.asStateFlow()
    var animeSearchPage = 1

    private val _mangaSearchUiState: MutableStateFlow<NetWorkState<List<SearchMangaQuery.Medium?>>> =
        MutableStateFlow(NetWorkState.Loading())
    val mangaSearchUiState = _mangaSearchUiState.asStateFlow()
    var mangaSearchPage = 1

    private val _charaSearchUiState: MutableStateFlow<NetWorkState<List<SearchCharacterQuery.Character?>>> =
        MutableStateFlow(NetWorkState.Loading())
    val charaSearchUiState = _charaSearchUiState.asStateFlow()
    var charaSearchPage = 1


    fun getAnimeSearch(
        page: Int = 1,
        searchKeyword: String
    ) {
        viewModelScope.launch {
            _animeSearchUiState.value = NetWorkState.Loading()
            repository.getAnimeSearch(searchKeyword).catch { e ->
                _animeSearchUiState.value = NetWorkState.Error(e.message.toString())
            }.collect {
                _animeSearchUiState.value = NetWorkState.Success(it.page!!.media!!)
            }
        }
    }

    fun getMangaSearch(
        page: Int = 1,
        searchKeyword: String,
    ) {
        viewModelScope.launch {
            _mangaSearchUiState.value = NetWorkState.Loading()
            repository.getMangaSearch(searchKeyword).catch { e ->
                _mangaSearchUiState.value = NetWorkState.Error(e.message.toString())
            }.collect {
                _mangaSearchUiState.value = NetWorkState.Success(it.page!!.media!!)
            }
        }
    }


    fun getCharacterSearch(
        page: Int = 1,
        searchKeyword: String,
    ) {
        viewModelScope.launch {
            _charaSearchUiState.value = NetWorkState.Loading()
            repository.getCharacterSearch(searchKeyword).catch { e ->
                _charaSearchUiState.value = NetWorkState.Error(e.message.toString())
            }.collect {
                _charaSearchUiState.value = NetWorkState.Success(it.page!!.characters!!)
            }
        }
    }

}