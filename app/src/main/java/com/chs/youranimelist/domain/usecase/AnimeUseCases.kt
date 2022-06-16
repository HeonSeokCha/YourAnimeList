package com.chs.youranimelist.domain.usecase

data class AnimeUseCases(
    val getAnime: GetAnimes,
    val deleteAnime: DeleteAnime,
    val addAnime: AddAnime,
)