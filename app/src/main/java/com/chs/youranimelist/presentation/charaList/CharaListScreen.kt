package com.chs.youranimelist.presentation.charaList

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
fun CharaListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "CharaListScreen")
    }
}