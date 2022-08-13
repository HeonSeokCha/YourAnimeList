package com.chs.youranimelist.presentation.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchMediaScreen(
    searchType: String,
    searchKeyWord: String,
    viewModel: SearchMediaViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current


    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        if (true) {
            items(10) {

            }
        } else {
            items(10) {

            }
        }
    }
}