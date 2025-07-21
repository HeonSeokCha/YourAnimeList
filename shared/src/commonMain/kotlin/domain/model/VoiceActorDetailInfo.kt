package domain.model

data class VoiceActorDetailInfo(
    val voiceActorInfo: VoiceActorInfo,
    val gender: String,
    val birthDate: String,
    val deathDate: String?,
    val homeTown: String?,
    val dateActive: String,
    val favorite: Int,
    val description: String,
    val relationCharaList: List<CharacterInfo> = emptyList(),
    val relationAnimeList: List<AnimeInfo> = emptyList()
)
