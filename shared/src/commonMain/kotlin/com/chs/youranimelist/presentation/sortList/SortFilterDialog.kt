package com.chs.youranimelist.presentation.sortList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chs.youranimelist.domain.model.SortFilter
import com.chs.youranimelist.presentation.common.ItemExpandSingleBox
import com.chs.youranimelist.presentation.common.ItemExpandingMultiBox

@Composable
fun SortFilterDialog(
    selectedSortFilter: SortFilter,
    sortOptions: SortOptions,
    onClick: (SortFilter) -> Unit
) {
    var selectSortOptions: SortFilter = remember { selectedSortFilter }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    ) {
        ItemExpandSingleBox(
            title = "Year",
            list = sortOptions.optionYears,
            initValue = selectedSortFilter.selectYear?.toString()
        ) {
            selectSortOptions = selectSortOptions.copy(selectYear = it?.second?.toInt())
        }

        ItemExpandSingleBox(
            title = "Season",
            list = sortOptions.optionSeason,
            initValue = selectedSortFilter.selectSeason
        ) {
            selectSortOptions = selectSortOptions.copy(selectSeason = it?.second)
        }

        ItemExpandSingleBox(
            title = "Sort By",
            list = sortOptions.optionSort,
            initValue = selectedSortFilter.selectSort.first()
        ) {
            if (it != null) {
                selectSortOptions = selectSortOptions.copy(selectSort = listOf(it.second))
            }
        }

        ItemExpandSingleBox(
            title = "Status By",
            list = sortOptions.optionStatus,
            initValue = selectedSortFilter.selectStatus
        ) {
            if (it != null) {
                selectSortOptions = selectSortOptions.copy(selectStatus = it?.second)
            }
        }

        ItemExpandingMultiBox(
            title = "Genres",
            list = sortOptions.optionGenres,
            initValue = selectedSortFilter.selectGenre
        ) {
            selectSortOptions = selectSortOptions.copy(selectGenre = it)
        }

        ItemExpandingMultiBox(
            title = "Tags",
            list = sortOptions.optionTags.map { it.first },
            initValue = selectedSortFilter.selectTags
        ) {
            selectSortOptions = selectSortOptions.copy(selectTags = it)
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    start = 8.dp,
                    end = 8.dp
                ),
            onClick = { onClick(selectSortOptions) }
        ) {
            Text("APPLY")
        }
    }
}