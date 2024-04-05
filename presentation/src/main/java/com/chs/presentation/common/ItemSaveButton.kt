package com.chs.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ItemSaveButton(
    isSave: Boolean,
    saveClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp
            ),
        onClick = { saveClick() }
    ) {
        if (isSave) {
            Text("SAVED")
        } else {
            Text("ADD MY LIST")
        }
    }
}