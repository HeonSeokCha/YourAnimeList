package presentation.browse.actor

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.model.VoiceActorDetailInfo
import presentation.UiConst
import kotlinx.coroutines.flow.Flow

data class ActorDetailState(
    val actorDetailInfo: VoiceActorDetailInfo? = null,
    val actorAnimeList: Flow<PagingData<Pair<CharacterInfo, AnimeInfo>>>? = null,
    val tabNames: List<String> = UiConst.ACTOR_DETAIL_TAB_LIST,
    val tabIdx: Int = 0,
    val selectOption: UiConst.SortType = UiConst.SortType.NEWEST,
    val isLoading: Boolean = true,
)