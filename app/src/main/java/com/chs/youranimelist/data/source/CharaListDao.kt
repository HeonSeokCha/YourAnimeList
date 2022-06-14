package com.chs.youranimelist.data.source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.chs.youranimelist.data.model.CharacterDto

@Dao
interface CharaListDao {

    @Query("SELECT * FROM character ORDER BY id DESC")
    fun getAllCharaList(): Flow<List<CharacterDto>>

    @Query("SELECT * FROM character where charaId = :charaId LIMIT 1")
    fun checkCharaList(charaId: Int): Flow<CharacterDto>

    @Query("SELECT * FROM character WHERE name LIKE '%' || :charaName || '%' ORDER BY id DESC")
    fun searchCharaList(charaName: String): Flow<List<CharacterDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharaList(character: CharacterDto): Long

    @Delete
    suspend fun deleteCharaList(character: CharacterDto)

}