package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.AnimeCharacterQuery
import com.chs.youranimelist.domain.repository.AnimeDetailRepository
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeCharaUseCase @Inject constructor(
    private val repository: AnimeDetailRepository
) {
    suspend operator fun invoke(animeId: Int): Flow<Resource<AnimeCharacterQuery.Data>> =
        repository.getAnimeCharacter(animeId)
}