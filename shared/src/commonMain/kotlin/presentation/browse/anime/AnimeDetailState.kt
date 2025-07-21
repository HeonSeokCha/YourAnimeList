package presentation.browse.anime

import androidx.paging.PagingData
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.AnimeThemeInfo
import kotlinx.coroutines.flow.Flow

data class AnimeDetailState(
    val animeDetailInfo: AnimeDetailInfo? = null,
    val animeThemes: AnimeThemeInfo = AnimeThemeInfo(),
    val animeRecList: Flow<PagingData<AnimeInfo>>? = null,
    val selectTabIdx: Int = 0,
    val isSave: Boolean? = null
)

