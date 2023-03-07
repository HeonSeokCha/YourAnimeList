package com.chs.youranimelist.mapper

import com.chs.CharacterDetailQuery
import com.chs.fragment.CharacterBasicInfo
import com.chs.youranimelist.domain.model.CharacterDetailInfo
import com.chs.youranimelist.domain.model.CharacterInfo

private fun convertCharacter(characterBasicInfo: CharacterBasicInfo?): CharacterInfo {
    return CharacterInfo(
        id = characterBasicInfo?.id ?: 0,
        name = characterBasicInfo?.name?.full ?: "",
        nativeName = characterBasicInfo?.name?.native ?: "",
        imageUrl = characterBasicInfo?.image?.large,
        favorites = characterBasicInfo?.favourites ?: 0
    )
}

fun CharacterDetailQuery.Data.toCharacterDetailInfo(): CharacterDetailInfo {
    return CharacterDetailInfo(
        characterInfo = convertCharacter(this.character?.characterBasicInfo),
        description = this.character?.description ?: "",
        animeList = this.character?.media?.nodes?.map {
            convertAnimeBasicInfo(it?.animeBasicInfo)
        } ?: emptyList()
    )
}