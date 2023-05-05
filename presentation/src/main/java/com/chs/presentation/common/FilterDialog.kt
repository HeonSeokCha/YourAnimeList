package com.chs.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    list: List<Pair<String, String>>,
    onClick: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = Modifier
            .background(color = Color.White)
            .padding(
                top = 64.dp,
                bottom = 64.dp,
                start = 16.dp,
                end = 16.dp
            ),
        onDismissRequest = onDismiss,
    ) {
        LazyColumn(
            modifier = Modifier.background(Color.Gray),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(list.size) { idx ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDismiss()
                            onClick(idx)
                        },
                    text = list[idx].first,
                    fontSize = 16.sp
                )
            }
        }
    }
}
