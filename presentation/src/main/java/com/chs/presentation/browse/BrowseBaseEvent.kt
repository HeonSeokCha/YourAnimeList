package com.chs.presentation.browse

sealed interface BrowseBaseEvent {
    data object OnCloseClick : BrowseBaseEvent
}