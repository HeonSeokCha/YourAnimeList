package com.chs.presentation.browse.character

import android.text.util.Linkify
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.chs.presentation.UiConst
import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.VoiceActorInfo
import com.chs.presentation.R
import com.chs.presentation.browse.CollapsingToolbarScaffold
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.common.ItemMessageDialog
import com.chs.presentation.common.ItemSaveButton
import com.chs.presentation.common.placeholder
import com.chs.presentation.toCommaFormat
import com.chs.presentation.ui.theme.Pink80
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun CharacterDetailScreenRoot(
    viewModel: CharacterDetailViewModel,
    onAnimeClick: (id: Int, idMal: Int) -> Unit,
    onVoiceActorClick: (id: Int) -> Unit,
    onCloseClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val charaEvent by viewModel.charaDetailEvent.collectAsStateWithLifecycle(CharaDetailEvent.Idle)
    val context = LocalContext.current

    LaunchedEffect(charaEvent) {
        when (charaEvent) {
            CharaDetailEvent.OnError -> {
                Toast.makeText(context, "Something error in load Data..", Toast.LENGTH_SHORT)
                    .show()
            }

            else -> Unit
        }
    }

    CharacterDetailScreen(
        state = state,
        onEvent = { sideEffect ->
            when (sideEffect) {

                is CharaDetailEvent.ClickButton.Anime -> {
                    onAnimeClick(sideEffect.id, sideEffect.idMal)
                }

                is CharaDetailEvent.ClickButton.VoiceActor -> {
                    onVoiceActorClick(sideEffect.id)
                }

                CharaDetailEvent.ClickButton.Close -> {
                    onCloseClick()
                }

                else -> viewModel.changeEvent(sideEffect)
            }
        }
    )
}

@Composable
fun CharacterDetailScreen(
    state: CharacterDetailState,
    onEvent: (CharaDetailEvent) -> Unit,
) {
    val pagingItem = state.animeList?.collectAsLazyPagingItems()
    val lazyVerticalStaggeredState = rememberLazyStaggeredGridState()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var descDialogShow by remember { mutableStateOf(false) }
    var spoilerDesc by remember { mutableStateOf("") }

    if (pagingItem != null) {
        when (pagingItem.loadState.refresh) {
            is LoadState.Loading -> {
            }

            is LoadState.Error -> {
            }

            else -> {
            }
        }

        when (pagingItem.loadState.append) {
            is LoadState.Loading -> {}

            is LoadState.Error -> {}

            else -> Unit
        }
    }

    CollapsingToolbarScaffold(
        scrollState = scrollState,
        header = {
            val characterDetailInfo = state.characterDetailInfo
            CharacterBanner(
                characterInfo = characterDetailInfo,
                isSave = state.isSave,
            ) {
                if (characterDetailInfo == null) return@CharacterBanner
                if (state.isSave == null) return@CharacterBanner
                if (state.isSave) {
                    onEvent(CharaDetailEvent.DeleteCharaInfo(characterDetailInfo.characterInfo))
                } else {
                    onEvent(CharaDetailEvent.InsertCharaInfo(characterDetailInfo.characterInfo))
                }
            }
        },
        isShowTopBar = true,
        onCloseClick = { onEvent(CharaDetailEvent.ClickButton.Close) }
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyVerticalStaggeredState,
            columns = StaggeredGridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalItemSpacing = 4.dp,
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
        ) {

            val characterDetailInfo = state.characterDetailInfo
            item(span = StaggeredGridItemSpan.FullLine) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {
                    if (characterDetailInfo != null) {
                        CharacterProfile(characterDetailInfo = characterDetailInfo)

                        CharacterDescription(
                            description = characterDetailInfo.spoilerDesc,
                        ) {
                            spoilerDesc = it
                            descDialogShow = true
                        }

                        if (!characterDetailInfo.voiceActorInfo.isNullOrEmpty()) {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(characterDetailInfo.voiceActorInfo!!) { actorInfo ->
                                    if (actorInfo != null) {
                                        CharacterVoiceActorInfo(actorInfo) { id ->
                                            onEvent(
                                                CharaDetailEvent.ClickButton.VoiceActor(id)
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

            if (pagingItem != null) {
                items(
                    count = pagingItem.itemCount,
                    key = pagingItem.itemKey { it.id }
                ) {
                    val anime = pagingItem[it]
                    if (anime != null) {
                        ItemAnimeSmall(
                            item = anime,
                            onClick = {
                                onEvent(
                                    CharaDetailEvent.ClickButton.Anime(
                                        id = anime.id,
                                        idMal = anime.idMal
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    if (descDialogShow) {
        ItemMessageDialog(
            message = spoilerDesc
        ) {
            spoilerDesc = ""
            descDialogShow = false
        }
    }
}


@Composable
private fun CharacterBanner(
    characterInfo: CharacterDetailInfo?,
    isSave: Boolean?,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp
            )
            .height(220.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(100)),
                model = characterInfo?.characterInfo?.imageUrl,
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
                        .placeholder(visible = characterInfo?.characterInfo?.name == null),
                    text = characterInfo?.characterInfo?.name ?: "Character PreView"
                )

                Text(
                    modifier = Modifier
                        .placeholder(visible = characterInfo?.characterInfo?.nativeName == null),
                    text = characterInfo?.characterInfo?.nativeName ?: "Character PreView"
                )

                Text(
                    text = buildAnnotatedString {
                        appendInlineContent(
                            UiConst.FAVOURITE_ID,
                            UiConst.FAVOURITE_ID
                        )
                        append(characterInfo?.characterInfo?.favourites.toCommaFormat)
                    },
                    inlineContent = UiConst.inlineContent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                )
            }
        }

        ItemSaveButton(isSave = isSave) {
            onClick()
        }
    }
}

@Composable
private fun CharacterProfile(
    characterDetailInfo: CharacterDetailInfo?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (characterDetailInfo != null) {
            if (characterDetailInfo.birthDay.isNotEmpty()) {
                ProfileText("Birthday", characterDetailInfo.birthDay)
            }

            if (characterDetailInfo.age.isNotEmpty()) {
                ProfileText("Age", characterDetailInfo.age)
            }

            if (characterDetailInfo.gender.isNotEmpty()) {
                ProfileText("Gender", characterDetailInfo.gender)
            }

            if (characterDetailInfo.bloodType.isNotEmpty()) {
                ProfileText("Blood Type", characterDetailInfo.bloodType)
            }
        } else {
            repeat(4) {
                ProfileText(null, null)
            }
        }
    }
}

@Composable
fun ProfileText(
    title: String?,
    values: String?
) {
    Row(
        modifier = Modifier
            .padding(bottom = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .placeholder(visible = values == null),
            text = "${title ?: UiConst.TITLE_PREVIEW}:  ",
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier
                .placeholder(visible = values == null),
            text = values ?: UiConst.TITLE_PREVIEW,
        )
    }
}


@Composable
private fun CharacterDescription(
    description: String?,
    onSpoilerClick: (String) -> Unit
) {
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
                markdown = description ?: stringResource(id = R.string.lorem_ipsum),
                linkifyMask = Linkify.EMAIL_ADDRESSES,
                syntaxHighlightColor = Pink80,
                onLinkClicked = {
                    onSpoilerClick(it.replace("%", " "))
                }
            )
        } else {
            MarkdownText(
                modifier = Modifier
                    .placeholder(visible = description == null),
                markdown = description ?: stringResource(id = R.string.lorem_ipsum),
                linkifyMask = Linkify.WEB_URLS,
                maxLines = 5,
                syntaxHighlightColor = Pink80,
                truncateOnTextOverflow = true,
                onLinkClicked = {
                    onSpoilerClick(it.replace("%", " "))
                }
            )
        }

        if (description != null && description.length > 200) {
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

@Composable
private fun CharacterVoiceActorInfo(
    info: VoiceActorInfo,
    onClick: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clickable {
                onClick(info.id)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(100)),
            placeholder = ColorPainter(Color.LightGray),
            model = info.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = info.name,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = info.language,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}