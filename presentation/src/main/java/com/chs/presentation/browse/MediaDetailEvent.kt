package com.chs.presentation.browse

sealed interface MediaDetailEvent {
    data object InsertMediaInfo : MediaDetailEvent
    data object DeleteMediaInfo : MediaDetailEvent
}