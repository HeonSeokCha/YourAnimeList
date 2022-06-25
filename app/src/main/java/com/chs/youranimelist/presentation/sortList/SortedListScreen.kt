package com.chs.youranimelist.presentation.sortList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chs.youranimelist.util.ConvertDate
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun SortedListScreen(
    sortType: String,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            Text(text = "Year")
            Text(text = "Season")
            Text(text = "Sort")
            Text(text = "Genre")
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
        ) {
//            ItemAnimeSmall(item = )
        }
    }
}

@Composable
private fun FilterList() {
    val yearList =
        ArrayList((ConvertDate.getCurrentYear(true) downTo 1970).map { it.toString() })
    LazyRow {
    }
}