package com.chs.presentation.browse

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.chs.presentation.pxToDp
import com.chs.presentation.ui.theme.Red500

private var isHeaderHide: Boolean = false

internal class BackgroundScrollConnection(
    private val scrollState: ScrollState
) : NestedScrollConnection {

    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        val dy = available.y

        return when {
            isHeaderHide -> {
                Offset.Zero
            }

            dy < 0 -> {
                scrollState.dispatchRawDelta(dy * -1)
                Offset(0f, dy)
            }

            else -> {
                Offset.Zero
            }
        }
    }
}

@Composable
fun CollapsingToolbarScaffold(
    scrollState: ScrollState,
    header: @Composable () -> Unit,
    isShowTopBar: Boolean,
    onCloseClick: () -> Unit,
    stickyHeader: @Composable () -> Unit = { },
    content: @Composable () -> Unit
) {
    val nestedScrollConnection = remember {
        BackgroundScrollConnection(scrollState)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var globalHeight by remember { mutableIntStateOf(0) }
        var visiblePercentage by remember { mutableFloatStateOf(0f) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = if (isShowTopBar) 56.dp else 0.dp)
                .onSizeChanged { size ->
                    globalHeight = size.height
                }
                .verticalScroll(scrollState)
                .nestedScroll(nestedScrollConnection)
        ) {
            Column {
                HeadSection(
                    header = header,
                    onHide = { isHide ->
                        isHeaderHide = isHide
                    }, onVisibleChange = { visiblePercentage = it }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(globalHeight.pxToDp())
                ) {
                    stickyHeader()
                    content()
                }
            }
        }

        if (isShowTopBar) {
            GradientTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .align(Alignment.TopStart)
                    .background(Red500),
                onCloseClick = onCloseClick
            )
        } else {
            GradientTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .alpha(1f - visiblePercentage)
                    .align(Alignment.TopStart)
                    .background(Red500),
                onCloseClick = onCloseClick
            )
        }
    }
}

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
    header: @Composable () -> Unit,
    onHide: (Boolean) -> Unit,
    onVisibleChange: (Float) -> Unit
) {
    var contentHeight by remember { mutableIntStateOf(0) }
    var visiblePercentage by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(visiblePercentage) {
        onVisibleChange(visiblePercentage)
        onHide(visiblePercentage <= 0f)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .onGloballyPositioned { layoutCoordinates ->
                visiblePercentage = layoutCoordinates.boundsInRoot().height / contentHeight
            }
            .onSizeChanged {
                contentHeight = it.height
            }
            .alpha(visiblePercentage)
    ) {
        header()
    }
}