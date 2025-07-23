package com.chs.youranimelist.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chs.youranimelist.presentation.UiConst
import org.jetbrains.compose.resources.painterResource

@Composable
fun ItemNoResultImage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ico_no_result),
            contentDescription = null
        )
    }
}

@Composable
fun ItemErrorImage(
    message: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ico_no_result),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = message ?: UiConst.TEXT_UNKNOWN_ERROR)
    }
}