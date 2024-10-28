package com.chs.presentation.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemExpandSingleBox(
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
                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = selectOptions ?: "Any",
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        ExposedDropdownMenu(
            matchTextFieldWidth = true,
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
