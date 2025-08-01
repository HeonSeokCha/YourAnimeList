package com.chs.youranimelist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.net.toUri
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.domain.model.MediaType
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.browse.BrowseApp

class BrowseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val activity = LocalActivity.current

            val browseInfo =
                if (intent?.getStringExtra(UiConst.TARGET_TYPE) == MediaType.MEDIA.name) {
                    BrowseInfo(
                        type = MediaType.MEDIA,
                        id = intent.getIntExtra(UiConst.TARGET_ID, 0),
                        idMal = intent.getIntExtra(UiConst.TARGET_ID_MAL, 0)
                    )
                } else {
                    BrowseInfo(
                        type = MediaType.CHARACTER,
                        id = intent.getIntExtra(UiConst.TARGET_ID, 0),
                    )
                }

            BrowseApp(
                browseInfo = browseInfo,
                onLinkClick = {
                    activity?.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            it.toUri()
                        )
                    )
                },
                onClose = { activity?.finish() }
            )
        }
    }
}