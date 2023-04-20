package com.chs.presentation.browse

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingAppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = White,
    minHeight: Dp = 56.dp,
    scrollBehavior: TopAppBarScrollBehavior,
    collapsingContent: @Composable () -> Unit = {},
    toolBarClick: () -> Unit
) {
    var collapsingContentHeight by remember { mutableStateOf(0) }
    val offset = scrollBehavior.state.heightOffset

    val minHeightPx = LocalDensity.current.run { minHeight.toPx() }

    SideEffect {
        val limit = minHeightPx - collapsingContentHeight.toFloat()
        if (scrollBehavior.state.heightOffsetLimit != limit) {
            scrollBehavior.state.heightOffsetLimit = limit
        }
    }

    val appBarDragModifier = if (!scrollBehavior.isPinned) {
        Modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scrollBehavior.state.heightOffset = offset + delta
            },
            onDragStopped = { velocity ->
                settleAppBar(
                    scrollBehavior.state,
                    velocity,
                    scrollBehavior.flingAnimationSpec,
                    scrollBehavior.snapAnimationSpec
                )
            }
        )
    } else Modifier

    val baseOffset =
        if (scrollBehavior.state.heightOffsetLimit > minHeightPx) -minHeightPx else (minHeightPx - collapsingContentHeight)
    val collapsedFraction = offset / baseOffset
    val collapsedContentAlpha =
        CubicBezierEasing(.8f, 0f, .8f, .15f).transform(collapsedFraction)
    val expandedContentAlpha = 1f - collapsedContentAlpha

    Surface(
        modifier = modifier
            .background(backgroundColor)
            .systemBarsPadding()
            .statusBarsPadding()
            .then(appBarDragModifier)
    ) {
        Layout(
            content = {
                Box(
                    modifier = Modifier
                        .layoutId("collapsingContent")
                        .alpha(expandedContentAlpha)
                ) { collapsingContent() }
                Box(
                    Modifier
                        .layoutId("toolbar")
                        .fillMaxWidth()
                        .height(minHeight)
                        .alpha(collapsedContentAlpha)
                        .background(MaterialTheme.colorScheme.primary),
                ) {
                    IconButton(
                        modifier = Modifier
                            .padding(
                                top = 4.dp,
                                start = 4.dp
                            )
                            .align(Alignment.CenterStart),
                        onClick = { toolBarClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            tint = White,
                            contentDescription = null
                        )
                    }
                }
            },
            modifier = Modifier
                .statusBarsPadding()
                .systemBarsPadding(),
        ) { measurables, constraints ->
            val ccPlaceable =
                measurables.first { it.layoutId == "collapsingContent" }.measure(constraints)
            val tbPlaceable =
                measurables.first { it.layoutId == "toolbar" }.measure(constraints)

            collapsingContentHeight =
                max(ccPlaceable.height, tbPlaceable.height)

            val maxWidth =
                listOf(ccPlaceable.width, tbPlaceable.width).max()
            val currentHeight =
                collapsingContentHeight + scrollBehavior.state.heightOffset

            layout(maxWidth, currentHeight.toInt()) {
                ccPlaceable.placeRelative(0, 0)
                tbPlaceable.placeRelative(0, 0)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
private suspend fun settleAppBar(
    state: TopAppBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?
): Velocity {
    if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) {
        return Velocity.Zero
    }
    var remainVelocity = velocity

    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity
        )
            .animateDecay(flingAnimationSpec) {
                val delta = value - lastValue
                val initialHeightOffset = state.heightOffset
                state.heightOffset = initialHeightOffset + delta
                val consumed = abs(initialHeightOffset - state.heightOffset)
                lastValue = value
                remainVelocity = this.velocity
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }
    }

    if (snapAnimationSpec != null) {
        if (state.heightOffset > 0 &&
            state.heightOffset > state.heightOffsetLimit
        ) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                if (state.collapsedFraction < 0.5f) {
                    0f
                } else {
                    state.heightOffset
                },
                animationSpec = snapAnimationSpec
            ) { state.heightOffset = value }
        }
    }
    return Velocity(0f, remainVelocity)
}