package com.chs.youranimelist.data.source

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CharaListDao {

    @Query("SELECT * FROM character ORDER BY id DESC")
    fun getAllCharaList(): Flow<List<Character>>

    @Query("SELECT * FROM character where charaId = :charaId LIMIT 1")
    fun checkCharaList(charaId: Int): Flow<Character>

    @Query("SELECT * FROM character WHERE name LIKE '%' || :charaName || '%' ORDER BY id DESC")
    fun searchCharaList(charaName: String): Flow<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharaList(character: Character): Long

    @Delete
    suspend fun deleteCharaList(character: Character)

}