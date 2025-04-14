package com.chs.presentation.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chs.presentation.ui.theme.Pink80
import com.chs.presentation.ui.theme.Red500

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

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {

        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            value = selectOptions ?: "Any",
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
            containerColor = Color.White
        ) {
            list.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option.first,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
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

    Spacer(Modifier.height(16.dp))
}
