package com.chs.youranimelist.presentation.home

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chs.youranimelist.HomeRecommendListQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.presentation.Screen
import com.chs.youranimelist.presentation.browse.BrowseActivity
import com.chs.youranimelist.util.Constant
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                state = pagerState,
                count = state.pagerList.size
            ) { idx ->
                ItemHomeBanner(
                    context = context,
                    banner = state.pagerList[idx]
                )
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = Color.DarkGray,
                inactiveColor = Color.LightGray,
                modifier = Modifier
                    .padding(16.dp),
            )
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
        }

        items(state.nestedList.size, key = { it }) { idx ->
            ItemAnimeSort(
                Constant.HOME_SORT_TILE[idx],
                state.nestedList[idx],
                navigator,
                context
            )
        }

    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Magenta)
        }
    }
}

@Composable
fun ItemHomeBanner(
    context: Context,
    banner: HomeRecommendListQuery.Medium?
) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        model = banner?.bannerImage,
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
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                tint = Color.Yellow
            )
            Spacer(modifier = Modifier.padding(end = 8.dp))
            Text(
                text = banner?.averageScore.toString(),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 12.sp,
            )
            Spacer(modifier = Modifier.padding(end = 8.dp))
            Icon(
                Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.Red
            )
            Spacer(modifier = Modifier.padding(end = 8.dp))
            Text(
                text = banner?.favourites.toString(),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 12.sp
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
                    navigator.navigate(Screen.SortListScreen.route)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemAnimeSmall(
    item: AnimeList,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(260.dp),
        elevation = 1.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                model = item.coverImage?.extraLarge,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                text = item.title?.english ?: item.title?.romaji.toString(),
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp
            )
            Text(
                text = item.status.toString(),
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}