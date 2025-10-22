package com.chs.youranimelist.presentation.browse.actor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.unit.dp
import be.digitalia.compose.htmlconverter.htmlToAnnotatedString
import com.chs.youranimelist.domain.model.VoiceActorDetailInfo
import com.chs.youranimelist.presentation.common.ProfileText
import com.chs.youranimelist.presentation.common.shimmer
import com.chs.youranimelist.presentation.getIdFromLink
import com.chs.youranimelist.presentation.isHrefContent
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.lorem_ipsum
import org.jetbrains.compose.resources.stringResource

@Composable
fun ActorProfileTab(
    info: VoiceActorDetailInfo?,
    onIntent: (ActorDetailIntent) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(8.dp)
    ) {

        if (info != null) {
            if (info.birthDate.isNotEmpty()) {
                ProfileText("Birthday", info.birthDate)
            }

            if (!info.deathDate.isNullOrEmpty()) {
                ProfileText("DeathDate", info.deathDate)
            }

            if (info.gender.isNotEmpty()) {
                ProfileText("Gender", info.gender)
            }

            if (info.dateActive.isNotEmpty()) {
                ProfileText("Years active", info.dateActive)
            }

            if (!info.homeTown.isNullOrEmpty()) {
                ProfileText("Home Town", info.homeTown)
            }
        } else {
            repeat(5) {
                ProfileText(null, null)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val desc = info?.description ?: stringResource(Res.string.lorem_ipsum)

        Text(
            modifier = Modifier
                .shimmer(visible = info == null),
            text = remember(desc) {
                htmlToAnnotatedString(
                    html = desc,
                    linkInteractionListener = { link ->
                        if (link !is LinkAnnotation.Url) return@htmlToAnnotatedString
                        if (!isHrefContent(link.url)) return@htmlToAnnotatedString
                        getIdFromLink(
                            link = link.url,
                            onAnime = { onIntent(ActorDetailIntent.ClickAnime(it, it)) },
                            onChara = { onIntent(ActorDetailIntent.ClickChara(it)) },
                            onBrowser = { onIntent(ActorDetailIntent.ClickLink(it)) }
                        )
                    }
                )
            }
        )
    }
}
