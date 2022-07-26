package com.chs.youranimelist.data.repository

import coil.network.HttpException
import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.domain.repository.CharacterDetailRepository
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CharacterDetailRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : CharacterDetailRepository {

    override suspend fun getCharacterDetail(charaId: Int): Flow<Resource<CharacterQuery.Data>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        apolloClient.query(CharacterQuery(charaId)).execute().data
                    )
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            }
        }
    }
}