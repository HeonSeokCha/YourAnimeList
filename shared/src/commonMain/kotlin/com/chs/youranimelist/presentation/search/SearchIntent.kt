package com.chs.youranimelist.presentation.search

sealed interface SearchIntent {

    data class ClickAnime(val id: Int, val idMal: Int) : SearchIntent
    data object LoadAnime : SearchIntent
    data object LoadCompleteAnime : SearchIntent

    data class ClickChara(val id: Int) : SearchIntent
    data object LoadChara : SearchIntent
    data object LoadCompleteChara : SearchIntent

    data class OnChangeTabIdx(val idx: Int) : SearchIntent
    data class OnChangeSearchQuery(val query: String) : SearchIntent
    data object OnError : SearchIntent
}