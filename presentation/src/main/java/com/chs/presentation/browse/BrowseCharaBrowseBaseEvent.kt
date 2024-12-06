package com.chs.presentation.browse

sealed interface BrowseCharaBrowseBaseEvent : BrowseBaseEvent {
    data class OnCharaClick(val id: Int) : BrowseCharaBrowseBaseEvent
}