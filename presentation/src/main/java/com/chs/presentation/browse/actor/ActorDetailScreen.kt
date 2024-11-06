package com.chs.presentation.browse.actor

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chs.common.Resource
import com.chs.domain.model.StudioDetailInfo
import com.chs.domain.model.VoiceActorInfo
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.browse.CollapsingToolbarScaffold
import com.chs.presentation.common.ItemPullToRefreshBox
import com.chs.presentation.ui.theme.Pink80
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ActorDetailScreen(
    state: ActorDetailState,
    onEvent: (ActorDetailEvent) -> Unit,
    onNavigate: (BrowseScreen) -> Unit
) {
    val activity = (LocalContext.current as? Activity)
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState { 3 }

    ItemPullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            coroutineScope.launch {
                scrollState.scrollTo(0)
                onEvent(ActorDetailEvent.GetActorDetailInfo)
                delay(500L)
                isRefreshing = false
            }
        }
    ) {

        CollapsingToolbarScaffold(
            scrollState = scrollState,
            header = {
                when (state.actorDetailInfo) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {

                    }

                    is Resource.Error -> {

                    }
                }

            },
            onCloseClick = {
                activity?.finish()
            }, stickyHeader = {

                TabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = Pink80
                        )
                    }
                ) {
                    state.tabNames.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    text = title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 12.sp,
                                )
                            },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            selectedContentColor = Pink80,
                            unselectedContentColor = Color.Gray
                        )
                    }
                }
            }
        ) {
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> {

                    }

                    1 -> {

                    }
                }
            }
        }
    }
}

@Composable
private fun ActorInfo(actorInfo: VoiceActorInfo?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 8.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
    }
}
