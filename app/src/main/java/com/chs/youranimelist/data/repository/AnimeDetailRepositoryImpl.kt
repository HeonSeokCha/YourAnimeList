package com.chs.youranimelist.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import coil.network.HttpException
import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.AnimeCharacterQuery
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeOverviewQuery
import com.chs.youranimelist.AnimeRecommendQuery
import com.chs.youranimelist.data.mapper.toAnimDetails
import com.chs.youranimelist.data.model.AnimeDetailDto
import com.chs.youranimelist.data.paging.AnimeRecPagingSource
import com.chs.youranimelist.data.source.KtorJikanService
import com.chs.youranimelist.domain.model.AnimeDetails
import com.chs.youranimelist.domain.repository.AnimeDetailRepository
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AnimeDetailRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val jikanClient: KtorJikanService
) : AnimeDetailRepository {

    override suspend fun getAnimeDetail(animeId: Int): Flow<Resource<AnimeDetailQuery.Data>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        apolloClient.query(AnimeDetailQuery(animeId)).execute().data
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

    override suspend fun getAnimeOverview(animeId: Int): Flow<Resource<AnimeOverviewQuery.Data>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        apolloClient.query(AnimeOverviewQuery(animeId)).execute().data
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error("Couldn't load date"))
            }
        }
    }

    override suspend fun getAnimeCharacter(animeId: Int): Flow<Resource<AnimeCharacterQuery.Data>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        apolloClient.query(AnimeCharacterQuery(animeId)).execute().data
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error("Couldn't load date"))
            }
        }
    }

    override fun getAnimeRecList(animeId: Int): Flow<PagingData<AnimeRecommendQuery.Edge>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            AnimeRecPagingSource(apolloClient, animeId)
        }.flow
    }


    override suspend fun getAnimeOverviewTheme(animeId: Int): Flow<Resource<AnimeDetails>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        jikanClient.getAnimeTheme(malId = animeId)!!.toAnimDetails()
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error("Couldn't load date"))
                Log.e("JikanError", e.message.toString())
            }
        }
    }
}