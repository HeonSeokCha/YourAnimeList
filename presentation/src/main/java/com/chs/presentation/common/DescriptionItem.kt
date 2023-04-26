package com.chs.presentation.common

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat

@Composable
fun DescriptionItem(
    description: String,
    expandedDescButton: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp
            )
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (expandedDescButton) {
            Text(
                text = HtmlCompat.fromHtml(
                    description,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()
            )
        } else {
            Text(
                text = HtmlCompat.fromHtml(
                    description,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString(),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (description.length > 500) {
            Button(
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp
                    ),
                onClick = { onClick() }
            ) {
                if (expandedDescButton) {
                    Icon(imageVector = Icons.Filled.ArrowUpward, contentDescription = null)
                } else {
                    Icon(
                        imageVector = Icons.Filled.ArrowDownward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}