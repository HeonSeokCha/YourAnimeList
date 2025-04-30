package com.chs.presentation.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ItemSpoilerDialog(
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            HtmlText(message)
        }, confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Confirm")
            }
        }
    )
}
