package com.chs.data.mapper

import com.chs.data.ActorDetailQuery
import com.chs.data.CharacterDetailQuery
import com.chs.data.Util
import com.chs.data.source.db.entity.CharacterEntity
import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.CharacterInfo
import com.chs.data.fragment.CharacterBasicInfo
import com.chs.domain.model.VoiceActorDetailInfo
import com.chs.domain.model.VoiceActorInfo


fun CharacterBasicInfo?.toCharacterInfo(): CharacterInfo {
    return CharacterInfo(
        id = this?.id ?: 0,
        name = this?.name?.full ?: "",
        nativeName = this?.name?.native ?: "",
        imageUrl = this?.image?.large,
        favourites = this?.favourites ?: 0
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
        description = this.description ?: "",
        spoilerDesc = Util.convertSpoilerSentenceForDesc(
            desc = this.description ?: "",
            list = Util.findSpoilerStringList(this.description ?: "")
        ),
        birthDay = with(this.dateOfBirth) {
            Util.convertToDateFormat(this?.year, this?.month, this?.day)
        },
        bloodType = this.bloodType ?: "",
        gender = this.gender ?: "",
        age = this.age ?: "",
        voiceActorInfo = this.media?.edges?.mapNotNull {
            it?.voiceActors?.mapNotNull { voiceActor ->
                voiceActor.toVoiceActorInfo()
            }
        }!!.flatten().distinctBy { it.name }
    )
}

fun CharacterDetailQuery.VoiceActor?.toVoiceActorInfo(): VoiceActorInfo {
    return VoiceActorInfo(
        id = this?.id ?: 0,
        name = this?.name?.userPreferred ?: "",
        nativeName = this?.name?.native ?: "",
        imageUrl = this?.image?.large,
        language = this?.languageV2 ?: ""
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

fun ActorDetailQuery.Data?.toVoiceActorDetailInfo(): VoiceActorDetailInfo {
    return VoiceActorDetailInfo(
        voiceActorInfo = VoiceActorInfo(
            id = this?.Staff?.id ?: 0,
            name = this?.Staff?.name?.userPreferred ?: "",
            nativeName = this?.Staff?.name?.native ?: "",
            language = this?.Staff?.language ?: "",
            imageUrl = this?.Staff?.image?.large
        ),
        gender = this?.Staff?.gender ?: "",
        birthDate = Util.convertToDateFormat(
            year = this?.Staff?.dateOfBirth?.year,
            month = this?.Staff?.dateOfBirth?.month,
            day = this?.Staff?.dateOfBirth?.day
        ),
        deathDate = Util.convertToDateFormat(
            year = this?.Staff?.dateOfDeath?.year,
            month = this?.Staff?.dateOfDeath?.month,
            day = this?.Staff?.dateOfDeath?.day
        ),
        homeTown = this?.Staff?.homeTown,
        dateActive = this?.Staff?.yearsActive?.run {
            if (this.isNotEmpty()) {
                if (this.size == 1) {
                    "${this.first()} - Present"
                } else {
                    "${this.first()} - ${this.last()}"
                }
            } else ""
        } ?: "",
        favorite = this?.Staff?.favourites ?: 0,
        description = this?.Staff?.description ?: "",
    )
}