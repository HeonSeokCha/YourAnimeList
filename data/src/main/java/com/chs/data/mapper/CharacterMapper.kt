package com.chs.data.mapper

import com.chs.data.CharacterDetailQuery
import com.chs.data.Util
import com.chs.data.source.db.entity.CharacterEntity
import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.CharacterInfo
import com.chs.data.fragment.CharacterBasicInfo


fun CharacterBasicInfo.toCharacterInfo(): CharacterInfo {
    return CharacterInfo(
        id = this.id,
        name = this.name?.full ?: "",
        nativeName = this.name?.native ?: "",
        imageUrl = this.image?.large,
        favourites = this.favourites ?: 0
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
                favourites = this.favourites ?: 0
            )
        },
        description = this.description?.split("!~")?.get(0) ?: "",
        spoilerDesc = this.description?.split("!~")?.get(1) ?: "",
        birthDay = with(this.dateOfBirth) {
            Util.convertToDateFormat(this?.year, this?.month, this?.day)
        },
        bloodType = this.bloodType ?: "",
        gender = this.gender ?: "",
        age = this.age ?: "",
        animeList = this.media?.nodes?.map {
            it?.animeBasicInfo.toAnimeInfo()
        } ?: emptyList()
    )
}

fun CharacterEntity.toCharacterInfo(): CharacterInfo {
    return CharacterInfo(
        id = this.id,
        name = this.name,
        nativeName = this.nativeName,
        imageUrl = this.imageUrl,
        favourites = this.favorite
    )
}

fun CharacterInfo.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        nativeName = this.nativeName,
        imageUrl = this.imageUrl,
        favorite = this.favourites
    )
}