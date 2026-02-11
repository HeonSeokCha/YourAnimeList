package com.chs.youranimelist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.main.MainApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            MainApp {
                context.startActivity(
                    Intent(
                        context,
                        BrowseActivity::class.java
                    ).apply {
                        this.putExtra(UiConst.TARGET_TYPE, it.type.name)
                        this.putExtra(UiConst.TARGET_ID, it.id)
                        this.putExtra(UiConst.TARGET_ID_MAL, it.idMal)
                    }
                )
            }
        }
    }
}