package com.chs.youranimelist.data.source.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.chs.youranimelist.data.model.CharacterDto

@Dao
abstract class CharaListDao : BaseDao<CharacterDto> {

    @Query("SELECT * FROM character ORDER BY id DESC")
    abstract fun getAllCharaList(): Flow<List<CharacterDto>>

    @Query("SELECT * FROM character where charaId = :charaId LIMIT 1")
    abstract fun checkCharaList(charaId: Int): Flow<CharacterDto>

    @Query("SELECT * FROM character WHERE name LIKE '%' || :charaName || '%' ORDER BY id DESC")
    abstract fun searchCharaList(charaName: String): Flow<List<CharacterDto>>
}