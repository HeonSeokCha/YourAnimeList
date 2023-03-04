package com.chs.youranimelist.data.source.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.chs.youranimelist.data.model.CharacterDto
import com.chs.youranimelist.data.source.db.model.CharacterEntity
import com.chs.youranimelist.domain.model.CharacterInfo

@Dao
abstract class CharaListDao : BaseDao<CharacterEntity> {

    @Query("SELECT * FROM character ORDER BY id DESC")
    abstract fun getAllCharaList(): Flow<List<CharacterInfo>>

    @Query("SELECT * FROM character where charaId = :charaId LIMIT 1")
    abstract fun checkCharaList(charaId: Int): Flow<CharacterInfo>

    @Query("SELECT * FROM character WHERE name LIKE '%' || :charaName || '%' ORDER BY id DESC")
    abstract fun searchCharaList(charaName: String): Flow<List<CharacterInfo>>
}