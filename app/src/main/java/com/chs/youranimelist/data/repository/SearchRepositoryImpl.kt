package com.chs.youranimelist.data.repository

import coil.network.HttpException
import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.domain.repository.SearchRepository
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : SearchRepository {

    override suspend fun searchAnime(
        page: Int,
        search: String
    ): Flow<Resource<SearchAnimeQuery.Data>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        apolloClient.query(SearchAnimeQuery(page, search)).execute().data
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

    override suspend fun searchManga(
        page: Int,
        search: String
    ): Flow<Resource<SearchMangaQuery.Data>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        apolloClient.query(SearchMangaQuery(page, search)).execute().data
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

    override suspend fun searchCharacter(
        page: Int,
        search: String
    ): Flow<Resource<SearchCharacterQuery.Data>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        apolloClient.query(SearchCharacterQuery(page, search)).execute().data
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