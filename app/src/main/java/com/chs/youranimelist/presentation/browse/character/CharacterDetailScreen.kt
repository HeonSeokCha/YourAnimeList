package com.chs.youranimelist.presentation.browse.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.youranimelist.util.color

@Composable
fun CharacterDetailScreen(
    charaId: Int,
    navController: NavController,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.getCharacterDetail(charaId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CharacterBanner(state)
    }
}


@Composable
fun CharacterBanner(state: CharacterDetailState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(100))
                .background(
                    color = "#ffffff".color
                ),
            model = state.characterDetailInfo?.character?.image?.large,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}