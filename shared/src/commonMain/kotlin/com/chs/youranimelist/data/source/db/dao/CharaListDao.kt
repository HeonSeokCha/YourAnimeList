package com.chs.data.source.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.chs.data.source.db.entity.CharacterEntity

@Dao
abstract class CharaListDao : BaseDao<CharacterEntity> {

    @Query("SELECT * FROM characterInfo ORDER BY createDate DESC")
    abstract fun getAllCharaList(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characterInfo where id = :charaId")
    abstract fun checkCharaList(charaId: Int): Flow<CharacterEntity?>

}