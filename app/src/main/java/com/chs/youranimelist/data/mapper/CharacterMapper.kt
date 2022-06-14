package com.chs.youranimelist.data.mapper

import com.chs.youranimelist.data.model.CharacterDto
import com.chs.youranimelist.domain.model.Character

fun CharacterDto.toCharacter(): Character {
    return Character(
        charaId = charaId,
        name = name ?: "",
        nativeName = nativeName ?: "",
        image = image ?: "",
        favourites = favourites ?: 0,
    )
}

fun Character.toCharacterDto() : CharacterDto {
    return CharacterDto(
        charaId = charaId,
        name = name ?: "",
        nativeName = nativeName ?: "",
        image = image ?: "",
        favourites = favourites ?: 0,
    )
}