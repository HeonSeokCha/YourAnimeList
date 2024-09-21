package com.chs.presentation.sortList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chs.domain.model.SortFilter
import com.chs.domain.model.TagInfo
import com.chs.presentation.UiConst
import kotlin.math.exp

@Composable
fun SortFilterDialog(
    selectedSortFilter: SortFilter,
    yearOptionList: List<Pair<String, String>>,
    seasonOptionList: List<Pair<String, String>>,
    sortOptionList: List<Pair<String, String>>,
    statusOptionList: List<Pair<String, String>>,
    genreOptionList: List<String>,
    tagOptionList: List<TagInfo>,
    onClick: (SortFilter) -> Unit
) {
    var sortOption: SortFilter = remember { SortFilter() }
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
            list = tagOptionList.map { it.name },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemExpandSingleBox(
    title: String,
    list: List<Pair<String, String>>,
    initValue: String?,
    selectValue: (Pair<String, String>?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectOptions by remember { mutableStateOf(list.find { it.second == initValue }?.first) }

    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(Modifier.height(8.dp))

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable),
            value = selectOptions ?: "Any",
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                    modifier = Modifier.menuAnchor(MenuAnchorType.SecondaryEditable)
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            list.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.first, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        selectOptions = option.first
                        selectValue(option)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }

    Spacer(Modifier.height(12.dp))
}

@Composable
private fun ItemChipOptions(
    title: String,
    list: List<Pair<String, String>>,
    initValue: String?,
    selectValue: (Pair<String, String>?) -> Unit
) {
    var selectIdx: Int by remember {
        if (initValue == null) {
            mutableIntStateOf(list.size)
        } else {
            mutableIntStateOf(
                list.indexOf(list.find { it.second == initValue }!!)
            )
        }
    }

    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(Modifier.height(8.dp))

    TabRow(
        selectedTabIndex = selectIdx,
        containerColor = Color.LightGray
    ) {
        list.forEach { options ->
            Row(
                modifier = Modifier
                    .clickable(onClick = {
                        if (selectIdx == list.indexOf(options)) {
                            selectIdx = list.size
                            selectValue(null)
                        } else {
                            selectIdx = list.indexOf(options)
                            selectValue(options)
                        }
                    })
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = options.first,
                    fontSize = 11.sp,
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    Spacer(Modifier.height(12.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemExpandingMultiBox(
    title: String,
    list: List<String>,
    initValue: List<String>?,
    selectValue: (List<String>?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedList: MutableList<String> = remember { initValue?.toMutableList() ?: mutableListOf() }

    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(Modifier.height(8.dp))

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable),
            value = if (selectedList.isNotEmpty()) {
                if (selectedList.size > 1) {
                    "${selectedList.first()} + ${selectedList.size - 1}"
                } else {
                    selectedList.first()
                }
            } else "Any",
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                    modifier = Modifier.menuAnchor(MenuAnchorType.SecondaryEditable),
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            list.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        if (selectedList.any { it == option }) {
                            selectedList.remove(option)
                        } else {
                            selectedList.add(option)
                        }

                        if (selectedList.isEmpty()) {
                            selectValue(null)
                        } else {
                            selectValue(selectedList)
                        }
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    leadingIcon = {
                        if (selectedList.any { it == option }) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                tint = Color.Black,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    }

    Spacer(Modifier.height(12.dp))
}
