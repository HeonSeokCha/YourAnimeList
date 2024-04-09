package com.chs.presentation.browse

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.chs.presentation.pxToDp

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
    header: @Composable () -> Unit,
    onCloseClick: () -> Unit,
    stickyHeader: @Composable () -> Unit = { },
    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()
    val nestedScrollConnection = remember {
        BackgroundScrollConnection(scrollState)
    }

    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)

                    .background(MaterialTheme.colorScheme.primary),
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(
                            top = 4.dp,
                            start = 4.dp
                        )
                        .align(Alignment.CenterStart),
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
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            var globalHeight by remember { mutableIntStateOf(0) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .onSizeChanged { size ->
                        globalHeight = size.height
                    }
                    .nestedScroll(
                        connection = nestedScrollConnection,
                    )
                    .verticalScroll(scrollState),
            ) {

                HeadSection(
                    header = header,
                    onHide = { isHide ->
                        isHeaderHide = isHide
                    }
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
    }
}

@Composable
private fun HeadSection(
    header: @Composable () -> Unit,
    onHide: (Boolean) -> Unit
) {
    var contentHeight by remember { mutableIntStateOf(0) }
    var visiblePercentage by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(visiblePercentage) {
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