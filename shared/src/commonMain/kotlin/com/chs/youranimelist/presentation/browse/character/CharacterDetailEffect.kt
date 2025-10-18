package com.chs.youranimelist.presentation.browse.character

sealed interface CharacterDetailEffect {
    data class NavigateAnimeDetail(val id: Int, val idMal: Int) : CharacterDetailEffect
    data class NavigateCharaDetail(val id: Int) : CharacterDetailEffect
    data class NavigateVoiceActor(val id: Int) : CharacterDetailEffect
    data class NavigateBrowser(val url: String) : CharacterDetailEffect
    data object NavigateClose : CharacterDetailEffect
}