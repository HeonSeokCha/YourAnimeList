package com.chs.youranimelist.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chs.youranimelist.presentation.UiConst

@Composable
fun ProfileText(
    title: String?,
    values: String?
) {
    Row(
        modifier = Modifier
            .padding(bottom = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .shimmer(visible = values == null),
            text = "${title ?: UiConst.TITLE_PREVIEW}:  ",
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier
                .shimmer(visible = values == null),
            text = values ?: UiConst.TITLE_PREVIEW,
        )
    }
}
