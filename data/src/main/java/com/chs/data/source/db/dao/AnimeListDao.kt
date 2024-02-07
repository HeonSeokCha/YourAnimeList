package com.chs.data.source.db.dao

import androidx.room.*
import com.chs.data.source.db.entity.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AnimeListDao : BaseDao<AnimeEntity> {

    @Query("SELECT * FROM animeInfo ORDER BY createDate DESC")
    abstract fun getAllAnimeList(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM animeInfo WHERE id = :animeId")
    abstract fun checkAnimeList(animeId: Int): Flow<AnimeEntity?>
}