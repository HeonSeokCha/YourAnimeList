package com.chs.youranimelist.presentation.animeList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AnimeListScreen(
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "AnimeListScreen")
    }
}