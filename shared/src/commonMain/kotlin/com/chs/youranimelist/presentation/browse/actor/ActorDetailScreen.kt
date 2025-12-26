package com.chs.youranimelist.presentation.browse.actor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.model.VoiceActorDetailInfo
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.common.CollapsingToolbarScaffold
import com.chs.youranimelist.presentation.common.GradientTopBar
import com.chs.youranimelist.presentation.common.ShimmerImage
import com.chs.youranimelist.presentation.common.shimmer
import com.chs.youranimelist.presentation.toCommaFormat
import com.chs.youranimelist.presentation.ui.theme.Pink80
import kotlinx.coroutines.flow.Flow

@Composable
fun ActorDetailScreenRoot(
    viewModel: ActorDetailViewModel,
    onAnimeClick: (id: Int, idMal: Int) -> Unit,
    onCharaClick: (id: Int) -> Unit,
    onLinkClick: (url: String) -> Unit,
    onCloseClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ActorDetailEffect.NavigateAnimeDetail -> onAnimeClick(effect.id, effect.idMal)
                is ActorDetailEffect.NavigateBrowser -> onLinkClick(effect.url)
                is ActorDetailEffect.NavigateCharaDetail -> onCharaClick(effect.id)
                ActorDetailEffect.NavigateClose -> onCloseClick()
            }
        }
    }

    ActorDetailScreen(
        state = state,
        pagingData = viewModel.pagingData,
        onIntent = viewModel::handleIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorDetailScreen(
    state: ActorDetailState,
    pagingData: Flow<PagingData<Pair<CharacterInfo, AnimeInfo>>>,
    onIntent: (ActorDetailIntent) -> Unit
) {
    val pagerState = rememberPagerState { 2 }
    val scrollState = rememberScrollState()

    LaunchedEffect(state.tabIdx) {
        pagerState.animateScrollToPage(state.tabIdx)
    }

    CollapsingToolbarScaffold(
        scrollState = scrollState,
        isShowTopBar = true,
        collapsedContent = { visiblePercentage ->
            GradientTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                onCloseClick = {
                    if (visiblePercentage > 0.5f) return@GradientTopBar
                    onIntent(ActorDetailIntent.ClickClose)
                }
            )
        },
        expandContent = { ActorInfo(actorInfo = state.actorDetailInfo) }
    ) {
        SecondaryTabRow(state.tabIdx) {
            state.tabNames.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp,
                        )
                    }, selected = state.tabIdx == index,
                    onClick = { onIntent(ActorDetailIntent.ClickTab(index)) },
                    selectedContentColor = Pink80,
                    unselectedContentColor = Color.Gray
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            key = { it }
        ) { page ->
            when (page) {
                0 -> {
                    ActorProfileTab(
                        info = state.actorDetailInfo,
                        onIntent = onIntent
                    )
                }

                1 -> {
                    ActorMediaTab(
                        state = state,
                        pagingData = pagingData,
                        onIntent = onIntent
                    )
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
            ShimmerImage(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(100)),
                url = actorInfo?.voiceActorInfo?.imageUrl,
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
                        .shimmer(visible = actorInfo == null),
                    text = actorInfo?.voiceActorInfo?.name ?: "Character PreView"
                )

                Text(
                    modifier = Modifier
                        .shimmer(visible = actorInfo == null),
                    text = actorInfo?.voiceActorInfo?.nativeName ?: "Character PreView"
                )

                Text(
                    text = buildAnnotatedString {
                        appendInlineContent(
                            UiConst.FAVOURITE_ID,
                            UiConst.FAVOURITE_ID
                        )
                        append(actorInfo?.favorite.toCommaFormat())

                    },
                    inlineContent = UiConst.inlineContent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                )
            }
        }
    }
}


