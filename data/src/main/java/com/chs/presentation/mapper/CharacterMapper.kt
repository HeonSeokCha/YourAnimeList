package com.chs.presentation.mapper

import com.chs.CharacterDetailQuery
import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.CharacterInfo
import com.chs.fragment.CharacterBasicInfo
import com.chs.presentation.source.db.model.CharacterEntity


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

fun CharacterEntity.toCharacterInfo(): CharacterInfo {
    return CharacterInfo(
        id = this.id,
        name = this.name,
        nativeName = this.nativeName,
        imageUrl = this.imageUrl,
        favorites = this.favorite
    )
}

fun CharacterInfo.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        nativeName = this.nativeName,
        imageUrl = this.imageUrl,
        favorite = this.favorites
    )
}