package com.chs.presentation.search

sealed class SearchEvent {

    data class ChangeSearchQuery(val query: String) : SearchEvent()
    data object GetSearchAnimeResult : SearchEvent()
    data object GetSearchCharaResult : SearchEvent()
}