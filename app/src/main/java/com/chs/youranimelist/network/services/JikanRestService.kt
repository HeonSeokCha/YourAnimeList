package com.chs.youranimelist.network.services

import com.chs.youranimelist.network.response.AnimeDetails
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface JikanRestService {

    @GET("anime/{malId}")
    fun getAnimeDetails(@Path("malId") malId: Int): Call<AnimeDetails>

    companion object {
        operator fun invoke(): JikanRestService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.jikan.moe/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JikanRestService::class.java)
        }
    }
}