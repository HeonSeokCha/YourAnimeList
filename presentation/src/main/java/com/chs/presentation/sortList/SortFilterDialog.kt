package com.chs.presentation.sortList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chs.domain.model.SortFilter
import com.chs.presentation.UiConst
import com.chs.presentation.common.ItemChipOptions
import com.chs.presentation.common.ItemExpandSingleBox
import com.chs.presentation.common.ItemExpandingMultiBox

@Composable
fun SortFilterDialog(
    selectedSortFilter: SortFilter,
    yearOptionList: List<Pair<String, String>>,
    seasonOptionList: List<Pair<String, String>>,
    sortOptionList: List<Pair<String, String>>,
    statusOptionList: List<Pair<String, String>>,
    genreOptionList: List<String>,
    tagOptionList: List<Pair<String, String?>>,
    onClick: (SortFilter) -> Unit
) {
    var sortOption: SortFilter = remember { selectedSortFilter }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        ItemExpandSingleBox(
            title = "Year",
            list = yearOptionList,
            initValue = selectedSortFilter.selectYear?.toString()
        ) {
            sortOption = sortOption.copy(selectYear = it?.second?.toInt())
        }

        ItemChipOptions(
            title = "Season",
            list = seasonOptionList,
            initValue = selectedSortFilter.selectSeason
        ) {
            sortOption = sortOption.copy(selectSeason = it?.second)
        }

        ItemExpandSingleBox(
            title = "Sort By",
            list = sortOptionList,
            initValue = selectedSortFilter.selectSort
        ) {
            sortOption =
                sortOption.copy(selectSort = it?.second ?: UiConst.SortType.TRENDING.rawValue)
        }

        ItemChipOptions(
            title = "Status By",
            list = statusOptionList,
            initValue = selectedSortFilter.selectStatus
        ) {
            sortOption = sortOption.copy(selectStatus = it?.second)
        }

        ItemExpandingMultiBox(
            title = "Genres",
            list = genreOptionList,
            initValue = selectedSortFilter.selectGenre
        ) {
            sortOption = sortOption.copy(selectGenre = it)
        }

        ItemExpandingMultiBox(
            title = "Tags",
            list = tagOptionList.map { it.first },
            initValue = selectedSortFilter.selectTags
        ) {
            sortOption = sortOption.copy(selectTags = it)
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    start = 8.dp,
                    end = 8.dp
                ),
            onClick = { onClick(sortOption) }
        ) {
            Text("APPLY")
        }
    }
}