package com.chs.youranimelist.data.repository

import coil.network.HttpException
import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.data.model.CharacterDto
import com.chs.youranimelist.data.source.AnimeListDatabase
import com.chs.youranimelist.domain.repository.CharacterRepository
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val db: AnimeListDatabase
) : CharacterRepository {

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
                emit(Resource.Error("Couldn't load data.."))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data.."))
            }
        }
    }

    override fun checkCharaList(charaId: Int): Flow<CharacterDto?> {
        return db.charaListDao.checkCharaList(charaId)
    }

    override fun getYourCharaList(): Flow<List<CharacterDto>> {
        return db.charaListDao.getAllCharaList()
    }

    override suspend fun insertCharacter(character: CharacterDto) {
        db.charaListDao.insert(character)
    }

    override suspend fun deleteCharacter(character: CharacterDto) {
        db.charaListDao.delete(character)
    }

    override fun searchCharaList(charaName: String): Flow<List<CharacterDto>> {
        return db.charaListDao.searchCharaList(charaName)
    }
}