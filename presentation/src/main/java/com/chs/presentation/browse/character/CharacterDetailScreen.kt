package com.chs.presentation.browse.character

import android.app.Activity
import android.text.TextUtils
import android.widget.TextView
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
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
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.chs.common.Resource
import com.chs.presentation.UiConst
import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.R
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.browse.CollapsingToolbarScaffold
import com.chs.presentation.browse.MediaDetailEvent
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.common.ItemSaveButton
import com.chs.presentation.common.PlaceholderHighlight
import com.chs.presentation.common.placeholder
import com.chs.presentation.common.shimmer

@Composable
fun CharacterDetailScreen(
    navController: NavController,
    state: CharacterDetailState,
    onEvent: (MediaDetailEvent<CharacterInfo>) -> Unit
) {

    val activity = (LocalContext.current as? Activity)
    var expandedDescButton by remember { mutableStateOf(false) }
    val pagingItem = state.animeList?.collectAsLazyPagingItems()
    val lazyVerticalStaggeredState = rememberLazyStaggeredGridState()
    val scrollState = rememberScrollState()

    CollapsingToolbarScaffold(
        scrollState = scrollState,
        header = {
            when (state.characterDetailInfo) {
                is Resource.Loading -> {
                    CharacterBanner(characterInfo = null, isSave = false) { }
                }

                is Resource.Success -> {
                    val characterDetailInfo = state.characterDetailInfo.data
                    CharacterBanner(
                        characterInfo = characterDetailInfo,
                        isSave = state.isSave,
                    ) {
                        if (characterDetailInfo != null) {
                            if (state.isSave) {
                                onEvent(MediaDetailEvent.DeleteMediaInfo(characterDetailInfo.characterInfo))
                            } else {
                                onEvent(MediaDetailEvent.InsertMediaInfo(characterDetailInfo.characterInfo))
                            }
                        }
                    }
                }

                is Resource.Error -> {

                }
            }

        },
        onCloseClick = {
            activity?.finish()
        }
    ) {

        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyVerticalStaggeredState,
            columns = StaggeredGridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalItemSpacing = 4.dp,
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            when (state.characterDetailInfo) {
                is Resource.Loading -> {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 8.dp,
                                    end = 8.dp
                                )
                        ) {
                            CharacterProfile(characterDetailInfo = null)

                            CharacterDescription(description = null, expandedDescButton = false) { }
                        }
                    }

                    items(12) {
                        ItemAnimeSmall(null)
                    }
                }

                is Resource.Success -> {
                    val characterDetailInfo = state.characterDetailInfo.data
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 8.dp,
                                    end = 8.dp
                                )
                        ) {
                            if (characterDetailInfo != null) {
                                CharacterProfile(characterDetailInfo = characterDetailInfo)

                                CharacterDescription(
                                    description = characterDetailInfo.description,
                                    expandedDescButton = expandedDescButton
                                ) {
                                    expandedDescButton = !expandedDescButton
                                }
                            }
                        }

                    }

                    if (pagingItem != null && pagingItem.itemCount != 0) {
                        items(
                            count = pagingItem.itemCount,
                            key = { pagingItem[it]?.id ?: it }
                        ) {
                            val anime = pagingItem[it]
                            if (anime != null) {
                                ItemAnimeSmall(
                                    item = anime,
                                    onClick = {
                                        navController.navigate(
                                            BrowseScreen.AnimeDetailScreen(
                                                id = anime.id,
                                                idMal = anime.idMal
                                            )
                                        )
                                    }
                                )
                            }
                        }

                        item(span = StaggeredGridItemSpan.FullLine) {
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }

                is Resource.Error -> {
                }
            }
        }
    }
}


@Composable
private fun CharacterBanner(
    characterInfo: CharacterDetailInfo?,
    isSave: Boolean,
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
                    text = characterInfo?.characterInfo?.name ?: "Character PreView"
                )

                Text(
                    text = characterInfo?.characterInfo?.nativeName ?: "Character PreView"
                )

                Text(
                    text = buildAnnotatedString {
                        appendInlineContent(
                            UiConst.FAVOURITE_ID,
                            UiConst.FAVOURITE_ID
                        )
                        append("${characterInfo?.characterInfo?.favourites ?: 0}")

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
    Column {

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

            ProfileText(null, null)

            ProfileText(null, null)

            ProfileText(null, null)

            ProfileText(null, null)

        }
    }
}

@Composable
private fun ProfileText(
    title: String?,
    values: String?
) {
    Row(
        modifier = Modifier
            .padding(bottom = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .placeholder(
                    visible = values == null,
                    highlight = PlaceholderHighlight.shimmer()
                ),
            text = "${title ?: UiConst.TITLE_PREVIEW}:  ",
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .placeholder(
                    visible = values == null,
                    highlight = PlaceholderHighlight.shimmer()
                ),
            text = values ?: UiConst.TITLE_PREVIEW,
        )
    }
}


@Composable
private fun CharacterDescription(
    description: String?,
    expandedDescButton: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val spannedText = AnnotatedString.fromHtml(
            htmlString = description ?: stringResource(id = R.string.lorem_ipsum)
        )

        if (expandedDescButton) {
            Text(
                text = spannedText,
                fontSize = 16.sp
            )
        } else {
            Text(
                modifier = Modifier
                    .placeholder(
                        visible = description == null,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                text = spannedText,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 5
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
                    onClick = { onClick() }
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