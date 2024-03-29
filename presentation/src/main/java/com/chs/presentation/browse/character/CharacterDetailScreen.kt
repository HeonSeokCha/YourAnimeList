package com.chs.presentation.browse.character

import android.app.Activity
import android.text.TextUtils
import android.widget.TextView
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.chs.presentation.UiConst
import com.chs.domain.model.CharacterDetailInfo
import com.chs.presentation.R
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.browse.CollapsingAppBar
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.common.ItemSaveButton
import com.google.accompanist.placeholder.material.placeholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    navController: NavController,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val activity = (LocalContext.current as? Activity)
    var expandedDescButton by remember { mutableStateOf(false) }
    val pagingItem = state.animeList?.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            CollapsingAppBar(
                scrollBehavior = scrollBehavior,
                collapsingContent = {
                    CharacterBanner(
                        characterInfo = state.characterDetailInfo,
                        isSave = state.isSave,
                    ) {
                        if (state.isSave) {
                            viewModel.deleteCharacter()
                        } else {
                            viewModel.insertCharacter()
                        }
                    }
                }, toolBarClick = {
                    activity?.finish()
                },
                isShowToolBar = true
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { it ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .padding(it),
            columns = StaggeredGridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalItemSpacing = 4.dp,
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            item(span = StaggeredGridItemSpan.FullLine) {
                Column(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp
                        )
                ) {
                    if (state.characterDetailInfo != null) {
                        CharacterProfile(characterDetailInfo = state.characterDetailInfo!!)
                    }

                    CharacterDescription(
                        description = state.characterDetailInfo?.description,
                        expandedDescButton = expandedDescButton
                    ) {
                        expandedDescButton = !expandedDescButton
                    }
                }
            }
            if (pagingItem != null) {
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
                                    "${BrowseScreen.AnimeDetailScreen.route}/" +
                                            "${anime.id}" +
                                            "/${anime.idMal}"
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
            .height(280.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(100))
                    .placeholder(visible = characterInfo == null),
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
                        .placeholder(characterInfo == null),
                    text = characterInfo?.characterInfo?.name ?: "Character PreView"
                )

                Text(
                    modifier = Modifier
                        .placeholder(characterInfo == null),
                    text = characterInfo?.characterInfo?.nativeName ?: "Character PreView"
                )

                Text(
                    modifier = Modifier
                        .placeholder(characterInfo == null),
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

        ItemSaveButton(
            shimmerVisible = characterInfo == null,
            isSave = isSave
        ) {
            onClick()
        }
    }
}

@Composable
private fun CharacterProfile(characterDetailInfo: CharacterDetailInfo) {
    Column {
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
    }
}

@Composable
private fun ProfileText(
    title: String,
    values: String?
) {
    Row(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .placeholder(values == null)
    ) {
        Text(
            text = "$title: ",
            fontWeight = FontWeight.Bold
        )
        Text(
            text = values ?: "$title Value PreView",
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
            .padding(
                bottom = 8.dp
            )
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val spannedText = HtmlCompat.fromHtml(
            description ?: stringResource(id = R.string.lorem_ipsum),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )

        if (expandedDescButton) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth(),
                factory = { TextView(it) },
                update = {
                    it.text = spannedText
                    it.setTextColor(android.graphics.Color.parseColor("#000000"))
                    it.textSize = 16f
                }
            )
        } else {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(description == null),
                factory = { TextView(it) },
                update = {
                    it.text = spannedText
                    it.maxLines = 5
                    it.ellipsize = TextUtils.TruncateAt.END
                    it.setTextColor(android.graphics.Color.parseColor("#000000"))
                    it.textSize = 16f
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