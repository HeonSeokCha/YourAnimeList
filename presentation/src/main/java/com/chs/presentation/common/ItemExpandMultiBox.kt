package com.chs.presentation.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chs.presentation.ui.theme.Red500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemExpandingMultiBox(
    title: String,
    list: List<String>,
    initValue: List<String>?,
    selectValue: (List<String>?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedList: MutableList<String> =
        remember { initValue?.toMutableList() ?: mutableListOf() }

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
            matchTextFieldWidth = false
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
                                tint = Red500,
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
