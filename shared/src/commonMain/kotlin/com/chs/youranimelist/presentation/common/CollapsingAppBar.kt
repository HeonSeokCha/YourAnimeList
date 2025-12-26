package com.chs.youranimelist.presentation.common

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.chs.youranimelist.presentation.pxToDp

internal class BackgroundScrollConnection(
    private val scrollState: ScrollState
) : NestedScrollConnection {

    private var isHeaderHide: Boolean = false

    fun changeHeaderValue(value: Boolean) {
        isHeaderHide = value
    }

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
    expandContent: @Composable () -> Unit = { },
    collapsedContent: @Composable (alpha: Float) -> Unit,
    isShowTopBar: Boolean = false,
    stickyContent: @Composable () -> Unit = { },
    content: @Composable () -> Unit
) {
    val nestedScrollConnection = remember {
        BackgroundScrollConnection(scrollState)
    }

    val rememberStickyContent = remember {
        movableContentOf { stickyContent() }
    }

    var globalHeight by remember { mutableIntStateOf(0) }
    var contentHeight by remember { mutableIntStateOf(0) }
    var stickyHeaderHeight by remember { mutableIntStateOf(0) }
    var collapsedHeight by remember { mutableIntStateOf(0) }
    var topBarPadding by remember { mutableIntStateOf(0) }

    val visiblePercentage by remember {
        derivedStateOf {
            if (contentHeight <= 0) return@derivedStateOf 1f

            ((contentHeight - scrollState.value).toFloat() / contentHeight).coerceIn(0f, 1f)
        }
    }

    LaunchedEffect(visiblePercentage) {
        nestedScrollConnection.changeHeaderValue(visiblePercentage <= 0f)
    }

    LaunchedEffect(collapsedHeight) {
        if (!isShowTopBar) return@LaunchedEffect
        topBarPadding = collapsedHeight
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { size ->
                    globalHeight = size.height
                }
                .verticalScroll(scrollState)
                .nestedScroll(nestedScrollConnection)
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 1.dp)
                        .padding(top = topBarPadding.pxToDp())
                        .onSizeChanged {
                            contentHeight = it.height.coerceAtLeast(1)
                        }
                ) {
                    expandContent()
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(stickyHeaderHeight.pxToDp())
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(globalHeight.pxToDp())
                ) {
                    content()
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = topBarPadding.pxToDp())
                .align(Alignment.TopStart)
                .offset {
                    val scrollProgress = scrollState.value.coerceAtMost(contentHeight)

                    val yOffset =
                        if ((contentHeight - scrollProgress) < (collapsedHeight - topBarPadding)) {
                            (collapsedHeight - topBarPadding)
                        } else {
                            contentHeight - scrollProgress
                        }

                    IntOffset(0, yOffset)
                }
                .onSizeChanged { size ->
                    stickyHeaderHeight = size.height
                }
        ) {
            rememberStickyContent()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 1.dp)
                .onSizeChanged {
                    collapsedHeight = it.height
                }
        ) {
            collapsedContent(visiblePercentage)
        }
    }
}

@Composable
fun GradientTopBar(
    modifier: Modifier,
    topBarIcon: ImageVector? = null,
    onIconClick: () -> Unit = {},
    onCloseClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(top = 32.dp)
            .height(56.dp)
    ) {
        IconButton(
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopStart),
            onClick = onCloseClick
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                tint = White,
                contentDescription = null
            )
        }

        if (topBarIcon != null) {
            IconButton(
                modifier = Modifier
                    .size(56.dp)
                    .align(Alignment.TopEnd),
                onClick = onIconClick
            ) {
                Icon(
                    imageVector = topBarIcon,
                    tint = White,
                    contentDescription = null
                )
            }
        }
    }
}
