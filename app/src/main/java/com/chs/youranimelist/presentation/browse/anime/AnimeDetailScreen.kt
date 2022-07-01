package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chs.youranimelist.presentation.browse.AnimeNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@AnimeNavGraph
@Destination
@Composable
fun AnimeDetailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

    }
}

@Composable
fun DetailBanner() {

}



