package com.chs.presentation.sortList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
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
import kotlin.math.exp

@Composable
fun SortFilterDialog(
    state: SortState,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        ItemExpandSingleBox(
            title = "Year",
            list = state.optionYears,
        ) { }

        Spacer(Modifier.height(12.dp))

        ItemChipOptions(
            title = "Season",
            list = state.optionSeason,
        ) { }

        Spacer(Modifier.height(12.dp))

        ItemExpandSingleBox(
            title = "Sort By",
            list = state.optionSort,
        ) { }

        Spacer(Modifier.height(12.dp))

        ItemChipOptions(
            title = "Status By",
            list = state.optionStatus,
        ) { }

        Spacer(Modifier.height(12.dp))

        ItemExpandingMultiBox(
            title = "Genres",
            list = state.optionGenres,
        ) { }

        Spacer(Modifier.height(12.dp))

        ItemExpandingMultiBox(
            title = "Tags",
            list = state.optionTags.map { it.name },
        ) { }

        Spacer(Modifier.height(12.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemExpandSingleBox(
    title: String,
    list: List<Pair<String, String?>>,
    selectValue: (Pair<String, String?>) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectOptions by remember { mutableStateOf("Any") }

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
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable),
            value = selectOptions,
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
}

@Composable
private fun ItemChipOptions(
    title: String,
    list: List<Pair<String, String?>>,
    selectValue: (Pair<String, String?>) -> Unit
) {
    var selectIdx: Int by remember { mutableIntStateOf(0) }

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
                        selectIdx = list.indexOf(options)
                        selectValue(options)
                    })
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = options.first,
                    fontSize = 9.sp,
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemExpandingMultiBox(
    title: String,
    list: List<String>,
    selectValue: (List<String>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectIdx: MutableList<Int> = remember { mutableListOf() }

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
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable),
            value = if (selectIdx.isNotEmpty()) {
                if (selectIdx.size > 1) {
                    "${list[selectIdx.first()]} + ${selectIdx.size - 1}"
                } else {
                    list[selectIdx.first()]
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
                        if (selectIdx.any { it == list.indexOf(option) }) {
                            selectIdx.remove(list.indexOf(option))
                        } else {
                            selectIdx.add(list.indexOf(option))
                        }
                        selectValue(selectIdx.map { list[it] })
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    leadingIcon = {
                        if (selectIdx.any { it == list.indexOf(option) }) {
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
}
