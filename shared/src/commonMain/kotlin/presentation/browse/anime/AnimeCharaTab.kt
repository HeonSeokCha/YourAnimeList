package presentation.browse.anime

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.CharacterInfo
import presentation.browse.BrowseScreen
import presentation.common.ShimmerImage
import presentation.common.placeholder

@Composable
fun AnimeCharaScreen(
    info: AnimeDetailInfo?,
    onClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(
            horizontal = 8.dp,
            vertical = 8.dp
        ),
        columns = GridCells.Fixed(3)
    ) {

        val charaInfoList = info?.characterList ?: emptyList()
        items(charaInfoList) { charaInfo ->
            CharaImageItem(charaInfo = charaInfo) {
                onClick(charaInfo.id)
            }
        }
    }
}

@Composable
fun CharaImageItem(
    charaInfo: CharacterInfo?,
    onClick: (BrowseScreen.CharacterDetail) -> Unit
) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clickable {
                if (charaInfo != null) {
                    onClick(
                        BrowseScreen.CharacterDetail(id = charaInfo.id)
                    )
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ShimmerImage(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(100)),
            url = charaInfo?.imageUrl
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier
                .placeholder(visible = charaInfo == null),
            text = charaInfo?.name ?: "Character",
            textAlign = TextAlign.Center
        )
    }
}
