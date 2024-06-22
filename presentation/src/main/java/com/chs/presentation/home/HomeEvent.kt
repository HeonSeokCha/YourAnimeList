package com.chs.presentation.home

sealed class HomeEvent {
    data object GetHomeData : HomeEvent()
}