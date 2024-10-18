package com.chs.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemMessageDialog(
    message: String,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(
        modifier = Modifier
            .wrapContentSize()
            .padding(vertical = 16.dp)
            .background(color = Color.White)
            .padding(16.dp),
        onDismissRequest = onDismiss,

        ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = message)

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    onDismiss()
                }
            ) { Text("Confirm") }
        }
    }
}
