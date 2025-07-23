package com.chs.youranimelist.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chs.youranimelist.presentation.ui.theme.Red200

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemChipOptions(
    title: String,
    list: List<Pair<String, String>>,
    initValue: String?,
    selectValue: (Pair<String, String>?) -> Unit
) {
    var selectIdx: Int by remember {
        if (initValue == null) {
            mutableIntStateOf(0)
        } else {
            mutableIntStateOf(
                list.indexOf(list.find { it.second == initValue }!!)
            )
        }
    }

    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(Modifier.height(8.dp))

    SecondaryTabRow(
        containerColor = Red200,
        selectedTabIndex = selectIdx
    ) {
        list.forEach { options ->
            Row(
                modifier = Modifier
                    .clickable(onClick = {
                        if (selectIdx == list.indexOf(options)) {
                            selectIdx = list.size
                            selectValue(null)
                        } else {
                            selectIdx = list.indexOf(options)
                            selectValue(options)
                        }
                    })
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = options.first,
                    fontSize = 11.sp,
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    Spacer(Modifier.height(12.dp))
}
