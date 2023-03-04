package com.chs.youranimelist.data.source.db.dao

import androidx.room.*
import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.data.source.db.model.AnimeEntity
import com.chs.youranimelist.domain.model.AnimeInfo
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AnimeListDao : BaseDao<AnimeEntity> {

    @Query("SELECT * FROM anime ORDER BY id DESC")
    abstract fun getAllAnimeList(): Flow<List<AnimeInfo>>

    @Query("SELECT * FROM anime WHERE animeId = :animeId LIMIT 1")
    abstract fun checkAnimeList(animeId: Int): Flow<AnimeInfo?>

    @Query("SELECT * FROM anime WHERE title LIKE '%' || :animeTitle || '%' ORDER BY id DESC")
    abstract fun searchAnimeList(animeTitle: String): Flow<List<AnimeInfo>>
}