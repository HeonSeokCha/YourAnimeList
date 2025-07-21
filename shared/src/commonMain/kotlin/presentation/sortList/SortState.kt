package presentation.sortList

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.SortFilter
import kotlinx.coroutines.flow.Flow

data class SortState(
    val animeSortPaging: Flow<PagingData<AnimeInfo>>? = null,
    val sortFilter: SortFilter = SortFilter(),
    val sortOptions: SortOptions = SortOptions(),
    val isRefresh: Boolean = false,
    val isShowDialog: Boolean = false,
    val isLoading: Boolean = true
)