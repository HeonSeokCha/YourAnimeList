package com.chs.presentation.browse

sealed interface MediaDetailEvent<T> {
    data class InsertMediaInfo<T>(val mediaInfo: T) : MediaDetailEvent<T>
    data class DeleteMediaInfo<T>(val mediaInfo: T) : MediaDetailEvent<T>
}