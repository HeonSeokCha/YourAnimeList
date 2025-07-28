package com.chs.youranimelist.presentation.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import be.digitalia.compose.htmlconverter.htmlToAnnotatedString

@Composable
fun ItemSpoilerDialog(
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(text = remember(message) { htmlToAnnotatedString(message) })
        }, confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Confirm")
            }
        }
    )
}
