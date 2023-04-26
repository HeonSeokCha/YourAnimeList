package com.chs.presentation.browse.character

import android.app.Activity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.domain.model.CharacterDetailInfo
import com.chs.presentation.LoadingIndicator
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.browse.CollapsingAppBar
import com.chs.presentation.color
import com.chs.presentation.common.DescriptionItem
import com.chs.presentation.common.ItemAnimeSmall

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    charaId: Int,
    navController: NavController,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    var expandedDescButton by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    LaunchedEffect(viewModel, context) {
        viewModel.getCharacterDetail(charaId)
        viewModel.isSaveCharacter(charaId)
    }

    Scaffold(
        topBar = {
            CollapsingAppBar(
                scrollBehavior = scrollBehavior,
                collapsingContent = {
                    CharacterBanner(
                        characterInfo = state.characterDetailInfo,
                        isSave = state.isSaveChara != null,
                    ) {
                        if (state.isSaveChara != null) {
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
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(it),
            columns = StaggeredGridCells.Adaptive(100.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalItemSpacing = 4.dp
        ) {
            if (state.characterDetailInfo != null) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                end = 8.dp
                            )
                    ) {
                        CharacterProfile(characterDetailInfo = state.characterDetailInfo)

                        Spacer(modifier = Modifier.height(16.dp))

                        DescriptionItem(
                            description = state.characterDetailInfo.description,
                            expandedDescButton = expandedDescButton
                        ) {
                            expandedDescButton = !expandedDescButton
                        }
                    }
                }
            }
            if (state.characterDetailInfo?.animeList != null) {
                items(state.characterDetailInfo.animeList) { anime ->
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
        }

        if (state.isLoading) {
            LoadingIndicator()
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
                    .background(
                        color = "#ffffff".color
                    ),
                model = characterInfo?.characterInfo?.imageUrl,
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
                Text(text = characterInfo?.characterInfo?.name ?: "")
                Text(text = characterInfo?.characterInfo?.nativeName ?: "")
                Row {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Text(text = characterInfo?.characterInfo?.favorites.toString())
                }
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = { onClick() }
        ) {
            if (isSave) {
                Text("SAVED")
            } else {
                Text("ADD MY LIST")
            }
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
    values: String
) {
    Row {
        Text(
            text = "$title: ",
            fontWeight = FontWeight.Bold
        )
        Text(
            text = values,
        )
    }
}