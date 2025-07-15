package com.chs.presentation.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.chs.presentation.ui.theme.Red500
import kotlin.math.roundToInt

@Composable
private fun GradientTopBar(
    modifier: Modifier,
    onCloseClick: () -> Unit
) {
    Box {
        Box(modifier = modifier)

        IconButton(
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopStart)
                .padding(
                    top = 4.dp,
                    start = 4.dp
                ),
            onClick = { onCloseClick() }
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                tint = White,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun HeadSection(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit,
    onVisibleChange: (Float) -> Unit
) {
    var contentHeight by remember { mutableIntStateOf(0) }
    var visiblePercentage by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(visiblePercentage) {
        onVisibleChange(visiblePercentage)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .onGloballyPositioned { layoutCoordinates ->
                visiblePercentage = layoutCoordinates.boundsInRoot().height / contentHeight
            }
            .onSizeChanged { contentHeight = it.height }
            .alpha(visiblePercentage)
    ) {
        header()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingLayout(
    onCloseClick: () -> Unit,
    isShowTopBar: Boolean,
    header: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var visiblePercentage by remember { mutableFloatStateOf(0f) }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .layout { measurable, constraints ->
                        val startPaddingCompensation = 16.dp.toPx().roundToInt()
                        val topPadding = if (isShowTopBar) 0 else (56.dp.toPx().roundToInt() / 2)
                        val adjustedConstraints = constraints.copy(
                            maxWidth = constraints.maxWidth + startPaddingCompensation
                        )
                        val placeable = measurable.measure(adjustedConstraints)
                        layout(placeable.width, placeable.height) {
                            placeable.place(-startPaddingCompensation / 2, -topPadding)
                        }
                    },
                title = {
                    HeadSection(
                        modifier = if (isShowTopBar) Modifier.padding(top = 28.dp) else Modifier,
                        header = header,
                        onVisibleChange = { visiblePercentage = it }
                    )
                },
                scrollBehavior = scrollBehavior
            )

            if (isShowTopBar) {
                GradientTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Red500),
                    onCloseClick = onCloseClick
                )
            } else {
                GradientTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .alpha(1f - visiblePercentage)
                        .background(Red500),
                    onCloseClick = onCloseClick
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            content()
        }
    }
}