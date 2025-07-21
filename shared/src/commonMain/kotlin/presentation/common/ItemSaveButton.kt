package presentation.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ItemSaveButton(
    modifier: Modifier = Modifier,
    isSave: Boolean?,
    saveClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .placeholder(visible = isSave == null),
        onClick = {
            if (isSave == null) return@Button
            saveClick()
        }
    ) {
        if (isSave == null) return@Button

        AnimatedContent(targetState = isSave) { isSave ->
            if (isSave) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )

                    Text("SAVED")
                }
            } else {
                Text("ADD MY LIST")
            }
        }
    }
}