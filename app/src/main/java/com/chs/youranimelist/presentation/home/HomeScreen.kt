package com.chs.youranimelist.presentation.home

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chs.HomeRecommendListQuery
import com.chs.fragment.AnimeList
import com.chs.youranimelist.presentation.main.Screen
import com.chs.youranimelist.presentation.browse.BrowseActivity
import com.chs.youranimelist.presentation.shimmerEffect
import com.chs.youranimelist.util.Constant

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navigator: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val pagerState = rememberPagerState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HorizontalPager(
                modifier = if (state.isLoading) {
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .shimmerEffect()
                } else {
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                },
                state = pagerState,
                pageCount = state.pagerList.size,
            ) { idx ->
                ItemHomeBanner(
                    context = context,
                    banner = state.pagerList[idx]
                )
            }
        }

        items(state.nestedList.size) { idx ->
            ItemAnimeSort(
                Constant.HOME_SORT_TILE[idx],
                state.nestedList[idx],
                navigator,
                context
            )
        }
        if (state.isLoading) {
            items(Constant.HOME_SORT_TILE.size) { idx ->
                ItemSortShimmer(
                    Constant.HOME_SORT_TILE[idx]
                )
            }
        }
    }
}

@Composable
fun ItemHomeBanner(
    context: Context,
    banner: AnimeList
) {
    val favoriteId = "favoriteId"
    val scoreId = "scoreId"
    val inlineContent = mapOf(
        Pair(
            scoreId,
            InlineTextContent(
                Placeholder(
                    width = 1.5.em,
                    height = 1.5.em,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Icon(
                    Icons.Rounded.Star,
                    contentDescription = null,
                    tint = Color.Yellow
                )
            }),
        Pair(
            favoriteId,
            InlineTextContent(
                Placeholder(
                    width = 1.5.em,
                    height = 1.5.em,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Icon(
                    Icons.Rounded.Favorite,
                    contentDescription = null,
                    tint = Color.Red,
                )
            }),
    )

    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        model = banner?.animeList?.bannerImage
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                context.startActivity(
                    Intent(
                        context, BrowseActivity::class.java
                    ).apply {
                        this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                        this.putExtra(Constant.TARGET_ID, banner?.id)
                        this.putExtra(Constant.TARGET_ID_MAL, banner?.idMal)
                    }
                )
            }
    ) {

        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 8.dp,
                    bottom = 32.dp
                ),
            text = banner?.title?.english ?: banner?.title?.romaji.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 8.dp,
                    bottom = 8.dp
                ),
        ) {
            Text(
                text = buildAnnotatedString {
                    appendInlineContent(scoreId, scoreId)
                    append(banner?.averageScore.toString())
                },
                inlineContent = inlineContent,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = buildAnnotatedString {
                    appendInlineContent(favoriteId, favoriteId)
                    append(banner?.favourites.toString())
                },
                inlineContent = inlineContent,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
fun ItemAnimeSort(
    title: String,
    list: List<AnimeList>,
    navigator: NavController,
    context: Context
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = {
                    navigator.navigate("${Screen.SortListScreen.route}/$title")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    tint = Color.Gray,
                    contentDescription = null
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .padding(bottom = 8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = list.size,
                key = {
                    list[it].id
                },
                itemContent = {
                    ItemAnimeSmall(
                        item = list[it],
                        onClick = {
                            context.startActivity(
                                Intent(
                                    context, BrowseActivity::class.java
                                ).apply {
                                    this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                                    this.putExtra(Constant.TARGET_ID, list[it].id)
                                    this.putExtra(Constant.TARGET_ID_MAL, list[it].idMal)
                                }
                            )
                        }
                    )
                },
            )
        }
    }
}

@Composable
fun ItemAnimeSmall(
    item: AnimeList,
    onClick: () -> Unit
) {
    val starId = "starId"
    val inlineContent = mapOf(
        Pair(
            starId,
            InlineTextContent(
                Placeholder(
                    width = 1.5.em,
                    height = 1.5.em,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Icon(
                    Icons.Rounded.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                )
            })
    )
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(280.dp)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 4.dp,
                    start = 4.dp,
                    end = 4.dp,
                    bottom = 4.dp
                )
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(5.dp)),
                model = item.coverImage?.extraLarge,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.title?.english ?: item.title?.romaji.toString(),
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp
            )
            Text(
                text = Constant.mediaStatus[item.status] ?: "",
                color = Constant.mediaStatusColor[item.status] ?: Color(0xFF888888),
                fontSize = 12.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 4.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                if (item.seasonYear != null) {
                    Text(
                        text = item.seasonYear.toString(),
                        color = Color.Gray,
                        fontSize = 12.sp,
                    )
                }

                if (item.averageScore != null) {
                    Text(
                        text = buildAnnotatedString {
                            appendInlineContent(starId, starId)
                            append(item.averageScore.toString())
                        },
                        inlineContent = inlineContent,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, false),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Composable
fun ItemSortShimmer(
    title: String
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    tint = Color.Gray,
                    contentDescription = null
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .padding(bottom = 8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = 6,
                itemContent = {
                    ItemAnimeSmallShimmer()
                }
            )
        }
    }
}

@Composable
fun ItemAnimeSmallShimmer() {
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(280.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 4.dp,
                    start = 4.dp,
                    end = 4.dp,
                    bottom = 4.dp
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .width(76.dp)
                    .height(12.dp)
                    .padding()
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(12.dp)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 4.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(12.dp)
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(12.dp)
                        .shimmerEffect()
                )
            }
        }
    }
}