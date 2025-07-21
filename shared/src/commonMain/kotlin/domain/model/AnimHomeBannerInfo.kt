package domain.model

data class AnimHomeBannerInfo(
    val animeInfo: AnimeInfo,
    val studioTitle: String,
    val description: String,
    val episode: Int,
    val startDate: String,
    val genres: List<String?> = List(6) { null }
)
