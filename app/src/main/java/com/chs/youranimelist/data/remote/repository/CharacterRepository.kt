package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.chs.youranimelist.browse.character.CharacterQuery
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacterDetail(charaId: Input<Int>): Flow<Response<CharacterQuery.Data>>

}