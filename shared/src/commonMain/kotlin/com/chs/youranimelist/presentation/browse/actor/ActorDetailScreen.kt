package com.chs.youranimelist.presentation.browse.actor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import be.digitalia.compose.htmlconverter.htmlToAnnotatedString
import com.chs.youranimelist.domain.model.VoiceActorDetailInfo
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.browse.CollapsingToolbarScaffold
import com.chs.youranimelist.presentation.browse.character.ProfileText
import com.chs.youranimelist.presentation.common.ShimmerImage
import com.chs.youranimelist.presentation.common.shimmer
import com.chs.youranimelist.presentation.toCommaFormat
import com.chs.youranimelist.presentation.ui.theme.Pink80
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.lorem_ipsum
import org.jetbrains.compose.resources.stringResource

@Composable
fun ActorDetailScreenRoot(
    viewModel: ActorDetailViewModel,
    onAnimeClick: (id: Int, idMal: Int) -> Unit,
    onCharaClick: (id: Int) -> Unit,
    onLinkClick: (url: String) -> Unit,
    onCloseClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val actorEvent by viewModel.actorEvent.collectAsStateWithLifecycle(ActorDetailEvent.Idle)

    LaunchedEffect(actorEvent) {
        when (actorEvent) {
            ActorDetailEvent.OnError -> {
            }

            else -> Unit
        }
    }

    ActorDetailScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is ActorDetailEvent.ClickBtn.Anime -> {
                    onAnimeClick(event.id, event.id)
                }

                is ActorDetailEvent.ClickBtn.Chara -> {
                    onCharaClick(event.id)
                }

                is ActorDetailEvent.ClickBtn.Link -> {
                    onLinkClick(event.url)
                }

                ActorDetailEvent.ClickBtn.Close -> {
                    onCloseClick()
                }

                else -> viewModel.changeEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorDetailScreen(
    state: ActorDetailState,
    onEvent: (ActorDetailEvent) -> Unit
) {
    val pagerState = rememberPagerState { 2 }
    val scrollState = rememberScrollState()

    LaunchedEffect(state.tabIdx) {
        pagerState.animateScrollToPage(state.tabIdx)
    }

    LaunchedEffect(pagerState.currentPage) {
        onEvent(ActorDetailEvent.ClickBtn.TabIdx(pagerState.currentPage))
    }

    CollapsingToolbarScaffold(
        scrollState = scrollState,
        header = { ActorInfo(actorInfo = state.actorDetailInfo) },
        isShowTopBar = true,
        onCloseClick = { onEvent(ActorDetailEvent.ClickBtn.Close) }
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
                    onClick = {
                        onEvent(ActorDetailEvent.ClickBtn.TabIdx(index))
                    },
                    selectedContentColor = Pink80,
                    unselectedContentColor = Color.Gray
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> {
                    VoiceActorProFile(state.actorDetailInfo) {
                        onEvent(it)
                    }
                }

                1 -> {
                    ActorMediaTab(
                        info = state.actorAnimeList,
                        sortOptionName = state.selectOption.name,
                        onAnimeClick = { id, idMal ->
                            onEvent(
                                ActorDetailEvent.ClickBtn.Anime(id, idMal)
                            )
                        }, onCharaClick = { id ->
                            onEvent(
                                ActorDetailEvent.ClickBtn.Chara(id)
                            )
                        }, onChangeSortEvent = { option ->
                            onEvent(
                                ActorDetailEvent.ChangeSortOption(option)
                            )
                        }
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

@Composable
private fun VoiceActorProFile(
    info: VoiceActorDetailInfo?,
    onEvent: (ActorDetailEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(8.dp)
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

            if (info.dateActive.isNotEmpty()) {
                ProfileText("Years active", info.dateActive)
            }

            if (!info.homeTown.isNullOrEmpty()) {
                ProfileText("Home Town", info.homeTown)
            }
        } else {
            repeat(5) {
                ProfileText(null, null)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val desc = info?.description ?: stringResource(Res.string.lorem_ipsum)

        Text(
            modifier = Modifier
                .shimmer(visible = info == null),
            text = remember(desc) { htmlToAnnotatedString(desc) }
        )
    }
}
