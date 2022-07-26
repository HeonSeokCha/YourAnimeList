package com.chs.youranimelist.domain.repository

import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterDetailRepository {
    suspend fun getCharacterDetail(charaId: Int): Flow<Resource<CharacterQuery.Data>>
}