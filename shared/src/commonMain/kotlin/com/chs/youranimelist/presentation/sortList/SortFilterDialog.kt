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
    state: SortState,
    onIntent: (SortIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    ) {
        ItemExpandSingleBox(
            title = "Year",
            list = state.sortOptions.optionYears,
            initValue = state.sortFilter.selectYear?.toString(),
            selectValue = { onIntent(SortIntent.OnChangeYear(it?.toInt())) }
        )

        ItemExpandSingleBox(
            title = "Season",
            list = state.sortOptions.optionSeason,
            initValue = state.sortFilter.selectSeason,
            selectValue = { onIntent(SortIntent.OnChangeSeason(it)) }
        )

        ItemExpandSingleBox(
            title = "Sort By",
            list = state.sortOptions.optionSort,
            initValue = state.sortFilter.selectSort.first()
        ) {
            if (it == null) return@ItemExpandSingleBox
            onIntent(SortIntent.OnChangeSort(it))
        }

        ItemExpandSingleBox(
            title = "Status By",
            list = state.sortOptions.optionStatus,
            initValue = state.sortFilter.selectStatus
        ) {
            if (it == null) return@ItemExpandSingleBox
            onIntent(SortIntent.OnChangeStatus(it))
        }

        ItemExpandingMultiBox(
            title = "Genres",
            list = state.sortOptions.optionGenres,
            initValue = state.sortFilter.selectGenre,
            selectValue = { onIntent(SortIntent.OnChangeGenres(it)) }
        )

        ItemExpandingMultiBox(
            title = "Tags",
            list = state.sortOptions.optionTags.map { it.first },
            initValue = state.sortFilter.selectTags,
            selectValue = { onIntent(SortIntent.OnChangeTags(it)) }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    start = 8.dp,
                    end = 8.dp
                ),
            onClick = { onIntent(SortIntent.ClickFilterApply) }
        ) {
            Text("APPLY")
        }
    }
}