package com.chs.presentation.browse.studio

sealed class StudioEvent {
    data class ChangeSortOption(val value: Pair<String, String>) : StudioEvent()
    data object GetStudioInfo : StudioEvent()
}