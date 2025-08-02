package com.chs.youranimelist.presentation.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chs.youranimelist.presentation.ui.theme.Red500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemExpandingMultiBox(
    title: String,
    list: List<String>,
    initValue: List<String>?,
    selectValue: (List<String>?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedList = remember {
        mutableStateListOf<String>().apply {
            initValue?.let { this.addAll(it) }
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
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
            label = { Text(text = title) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedPlaceholderColor = Color.White,
                focusedPlaceholderColor = Color.White
            )
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

    Spacer(Modifier.height(16.dp))
}
