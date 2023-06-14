package com.chs.domain.usecase

import com.chs.common.Resource
import com.chs.domain.model.AnimeThemeInfo
import com.chs.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//class GetAnimeThemeUseCase @Inject constructor(
//    private val repository: AnimeRepository
//) {
//    suspend operator fun invoke(animeId: Int): Flow<Resource<AnimeThemeInfo>> {
//        return flow {
//            emit(Resource.Loading())
//            try {
//                emit(Resource.Success(repository.getAnimeDetailTheme(animeId)))
//            } catch (e: Exception) {
//                emit(Resource.Error(e.message.toString()))
//            }
//        }
//    }
//}