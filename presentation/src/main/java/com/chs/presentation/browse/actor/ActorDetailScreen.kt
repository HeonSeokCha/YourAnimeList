package com.chs.presentation.browse.actor

import android.app.Activity
import android.text.util.Linkify
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.common.Resource
import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.StudioDetailInfo
import com.chs.domain.model.VoiceActorDetailInfo
import com.chs.domain.model.VoiceActorInfo
import com.chs.presentation.R
import com.chs.presentation.UiConst
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.browse.CollapsingToolbarScaffold
import com.chs.presentation.browse.character.ProfileText
import com.chs.presentation.common.ItemPullToRefreshBox
import com.chs.presentation.common.PlaceholderHighlight
import com.chs.presentation.common.placeholder
import com.chs.presentation.common.shimmer
import com.chs.presentation.ui.theme.Pink80
import dev.jeziellago.compose.markdowntext.MarkdownText
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
                        ActorInfo(actorInfo = null)
                    }

                    is Resource.Success -> {
                        ActorInfo(actorInfo = state.actorDetailInfo.data)
                    }

                    is Resource.Error -> {}
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
            }) {
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> {
                        ActorAnimeTab(state.actorAnimeList) {
                            onNavigate(it)
                        }
                    }

                    1 -> {
                        ActorCharaTab(state.actorCharaList) {
                            onNavigate(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ActorInfo(actorInfo: VoiceActorDetailInfo?) {
    Column(
        modifier = Modifier
            .padding(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(100)),
                model = actorInfo?.voiceActorInfo?.imageUrl,
                placeholder = ColorPainter(Color.LightGray),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 8.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    modifier = Modifier
                        .placeholder(
                            visible = actorInfo == null,
                            highlight = PlaceholderHighlight.shimmer()
                        ),
                    text = actorInfo?.voiceActorInfo?.name ?: "Character PreView"
                )

                Text(
                    modifier = Modifier
                        .placeholder(
                            visible = actorInfo == null,
                            highlight = PlaceholderHighlight.shimmer()
                        ),
                    text = actorInfo?.voiceActorInfo?.nativeName ?: "Character PreView"
                )

                Text(
                    text = buildAnnotatedString {
                        appendInlineContent(
                            UiConst.FAVOURITE_ID,
                            UiConst.FAVOURITE_ID
                        )
                        append(
                            String.format("%,d", actorInfo?.favorite ?: 0)
                        )

                    },
                    inlineContent = UiConst.inlineContent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        VoiceActorProfile(info = actorInfo)

        Spacer(modifier = Modifier.height(16.dp))


    }
}

@Composable
private fun VoiceActorProfile(
    info: VoiceActorDetailInfo?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (info != null) {
            if (info.birthDate.isNotEmpty()) {
                ProfileText("Birthday", info.birthDate)
            }

            if (!info.deathDate.isNullOrEmpty()) {
                ProfileText("DeathDate", info.deathDate)
            }

            if (info.gender.isNotEmpty()) {
                ProfileText("Gender", info.gender)
            }

            if (!info.homeTown.isNullOrEmpty()) {
                ProfileText("Home Town", info.homeTown)
            }
        } else {
            repeat(4) {
                ProfileText(null, null)
            }
        }
    }
}

@Composable
private fun VoiceActorDesc(desc: String?) {
    var expandedDescButton by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (expandedDescButton) {
            MarkdownText(
                markdown = desc ?: stringResource(id = R.string.lorem_ipsum),
                linkifyMask = Linkify.EMAIL_ADDRESSES,
                syntaxHighlightColor = Pink80,
                onLinkClicked = {
                }
            )
        } else {
            MarkdownText(
                modifier = Modifier
                    .placeholder(
                        visible = desc == null,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                markdown = desc ?: stringResource(id = R.string.lorem_ipsum),
                linkifyMask = Linkify.WEB_URLS,
                maxLines = 5,
                syntaxHighlightColor = Pink80,
                truncateOnTextOverflow = true,
                onLinkClicked = {
                }
            )
        }
        if (desc != null && desc.length > 200) {
            if (!expandedDescButton) {
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(
                            top = 8.dp,
                            bottom = 8.dp
                        ),
                    onClick = {
                        expandedDescButton = !expandedDescButton
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDownward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
