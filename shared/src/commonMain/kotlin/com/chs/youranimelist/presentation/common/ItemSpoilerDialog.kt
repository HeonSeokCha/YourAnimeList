package com.chs.youranimelist.presentation.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.LinkAnnotation
import be.digitalia.compose.htmlconverter.htmlToAnnotatedString
import com.chs.youranimelist.presentation.browse.character.CharaDetailIntent
import com.chs.youranimelist.presentation.getIdFromLink
import com.chs.youranimelist.presentation.isHrefContent

@Composable
fun ItemSpoilerDialog(
    message: String,
    onAnimeClick: (Int, Int) -> Unit,
    onCharacterClick: (Int) -> Unit,
    onBrowserClick: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = remember(message) {
                    htmlToAnnotatedString(
                        html = message,
                        linkInteractionListener = { link ->
                            if (link !is LinkAnnotation.Url) return@htmlToAnnotatedString
                            if (!isHrefContent(link.url)) return@htmlToAnnotatedString
                            onDismiss()
                            getIdFromLink(
                                link = link.url,
                                onAnime = { onAnimeClick(it, it) },
                                onChara = { onCharacterClick(it) },
                                onBrowser = { onBrowserClick(it) }
                            )
                        }
                    )
                }
            )
        }, confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Confirm")
            }
        }
    )
}
