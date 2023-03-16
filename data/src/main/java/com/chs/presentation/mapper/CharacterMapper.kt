package com.chs.presentation.mapper

import com.chs.CharacterDetailQuery
import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.CharacterInfo
import com.chs.fragment.CharacterBasicInfo


fun CharacterBasicInfo.toCharacterInfo(): CharacterInfo {
    return CharacterInfo(
        id = this.id,
        name = this.name?.full ?: "",
        nativeName = this.name?.native ?: "",
        imageUrl = this.image?.large,
        favorites = this.favourites ?: 0
    )
}

fun CharacterDetailQuery.Character.toCharacterDetailInfo(): CharacterDetailInfo {
    return CharacterDetailInfo(
        characterInfo = with(this.characterBasicInfo) {
            CharacterInfo(
                id = this.id,
                name = this.name?.full ?: "",
                nativeName = this.name?.native ?: "",
                imageUrl = this.image?.large,
                favorites = this.favourites ?: 0
            )
        },
        description = this.description ?: "",
        animeList = this.media?.nodes?.map {
            convertAnimeBasicInfo(it?.animeBasicInfo)
        } ?: emptyList()
    )
}