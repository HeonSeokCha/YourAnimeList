package com.chs.youranimelist.domain.usecase

data class AnimeUseCases(
    val getAnime: GetYourAnimes,
    val deleteAnime: DeleteYourAnime,
    val addYourAnime: AddYourAnime,
)