package com.chs.presentation.browse.animeimport android.annotation.SuppressLintimport androidx.compose.animation.animateContentSizeimport androidx.compose.animation.core.Springimport androidx.compose.animation.core.springimport androidx.compose.foundation.ExperimentalFoundationApiimport androidx.compose.foundation.clickableimport androidx.compose.foundation.combinedClickableimport androidx.compose.foundation.layout.*import androidx.compose.foundation.lazy.LazyRowimport androidx.compose.foundation.lazy.grid.GridCellsimport androidx.compose.foundation.lazy.grid.LazyVerticalGridimport androidx.compose.foundation.lazy.grid.itemsimport androidx.compose.foundation.lazy.itemsimport androidx.compose.foundation.rememberScrollStateimport androidx.compose.foundation.verticalScrollimport androidx.compose.material.icons.Iconsimport androidx.compose.material.icons.filled.ArrowDownwardimport androidx.compose.material3.*import androidx.compose.runtime.*import androidx.compose.ui.Alignmentimport androidx.compose.ui.Modifierimport androidx.compose.ui.graphics.Colorimport androidx.compose.ui.res.stringResourceimport androidx.compose.ui.text.AnnotatedStringimport androidx.compose.ui.text.font.FontWeightimport androidx.compose.ui.text.fromHtmlimport androidx.compose.ui.text.style.TextOverflowimport androidx.compose.ui.unit.dpimport androidx.compose.ui.unit.spimport com.chs.common.Resourceimport com.chs.domain.model.AnimeDetailInfoimport com.chs.domain.model.AnimeInfoimport com.chs.domain.model.AnimeRelationInfoimport com.chs.domain.model.AnimeThemeInfoimport com.chs.domain.model.TagInfoimport com.chs.presentation.Rimport com.chs.presentation.UiConstimport com.chs.presentation.UiConst.GENRE_COLORimport com.chs.presentation.browse.BrowseScreenimport com.chs.presentation.colorimport com.chs.presentation.common.ItemAnimeSmallimport com.chs.presentation.common.ItemMessageDialogimport com.chs.presentation.common.PlaceholderHighlightimport com.chs.presentation.common.placeholderimport com.chs.presentation.common.shimmerimport com.chs.presentation.isNotEmptyValueimport com.chs.presentation.toCommaFormatimport com.chs.presentation.ui.theme.Red200import com.chs.presentation.ui.theme.Red500@Composablefun AnimeOverViewScreen(    animeDetailState: AnimeDetailInfo?,    animeThemeState: AnimeThemeInfo?,    onEvent: (AnimeDetailEvent) -> Unit) {    var descDialogShow by remember { mutableStateOf(false) }    var tagValue by remember { mutableStateOf("") }    val scrollState = rememberScrollState()    Column(        modifier = Modifier            .fillMaxSize()            .padding(                start = 4.dp,                end = 8.dp,                bottom = 16.dp            )            .verticalScroll(scrollState)    ) {        if (animeThemeState == null) {            ItemLoading()        } else {            AnimeGenreChips(animeDetailState?.genres ?: List(3) { null }) {                onEvent(AnimeDetailEvent.OnGenreClick(it))            }            AnimeDescription(description = animeDetailState?.description)            AnimeScoreInfo(animeDetailInfo = animeDetailState)            AnimeSummaryInfo(                animeDetailInfo = animeDetailState,                onSeasonYearClick = { year, season ->                    onEvent(AnimeDetailEvent.OnSeasonYearClick(year = year, season = season))                },                onStudioClick = { id ->                    onEvent(AnimeDetailEvent.OnStudioClick(id))                }            )            if (animeDetailState?.tags != null) {                ItemAnimeTags(                    tags = animeDetailState.tags!!,                    onClick = {                        onEvent(AnimeDetailEvent.OnTagClick(it))                    },                    onLongClick = {                        tagValue = it                        descDialogShow = true                    }                )            }            ItemAnimeThemes(animeThemeState)            if (animeDetailState?.animeRelationInfo != null) {                AnimeRelationInfo(animeList = animeDetailState.animeRelationInfo) {                    onEvent(AnimeDetailEvent.OnAnimeClick(id = it.id, idMal = it.idMal))                }            }        }    }    if (descDialogShow) {        ItemMessageDialog(            message = tagValue        ) {            tagValue = ""            descDialogShow = false        }    }}@Composableprivate fun ItemAnimeThemes(animeTheme: AnimeThemeInfo?) {    if (animeTheme?.openingThemes != null && animeTheme.openingThemes.isNotEmpty()) {        AnimeThemeInfo(            title = "Opening Theme",            songList = animeTheme.openingThemes        )    }    if (animeTheme?.endingThemes != null && animeTheme.endingThemes.isNotEmpty()) {        AnimeThemeInfo(            title = "Ending Theme",            songList = animeTheme.endingThemes        )    }}@Composableprivate fun ItemLoading() {    AnimeGenreChips(list = List(3) { null }) { }    AnimeDescription(description = null)    AnimeScoreInfo(animeDetailInfo = null)    AnimeSummaryInfo(        animeDetailInfo = null,        onStudioClick = {},        onSeasonYearClick = { _, _ -> }    )    AnimeThemeInfo(        title = null,        songList = List(3) { null }    )    AnimeThemeInfo(        title = null,        songList = List(3) { null }    )}@OptIn(ExperimentalLayoutApi::class)@Composableprivate fun AnimeGenreChips(    list: List<String?>,    onGenreClick: (String) -> Unit) {    var maxLines by remember { mutableIntStateOf(1) }    ContextualFlowRow(        modifier = Modifier            .animateContentSize(),        itemCount = list.count(),        maxLines = maxLines,        overflow = ContextualFlowRowOverflow.expandOrCollapseIndicator(            minRowsToShowCollapse = 2,            expandIndicator = {                AssistChip(                    label = {                        Text(                            text = "${this@expandOrCollapseIndicator.totalItemCount - this@expandOrCollapseIndicator.shownItemCount}+ more"                        )                    },                    onClick = { maxLines += 1 },                    colors = AssistChipDefaults.assistChipColors(                        containerColor = Red200,                        labelColor = Color.White                    ), border = AssistChipDefaults.assistChipBorder(                        enabled = true,                        borderColor = Red200                    )                )            },            collapseIndicator = {//                AssistChip(//                    label = {//                        Text("Hide")//                    },//                    trailingIcon = {//                        Icon(//                            modifier = Modifier.size(16.dp),//                            imageVector = Icons.Default.Close,//                            tint = Color.White,//                            contentDescription = null//                        )//                    },//                    onClick = { maxLines = 1 },//                    colors = AssistChipDefaults.assistChipColors(//                        containerColor = Red700,//                        labelColor = Color.White//                    ), border = AssistChipDefaults.assistChipBorder(//                        enabled = true,//                        borderColor = Red700//                    )//                )            }        ),        horizontalArrangement = Arrangement.spacedBy(8.dp)    ) { idx ->        val genre = list[idx]        AssistChip(            modifier = Modifier                .placeholder(visible = genre == null),            onClick = {                if (genre != null) {                    onGenreClick(genre)                }            }, label = {                Text(text = genre ?: "Unknown")            }, colors = AssistChipDefaults.assistChipColors(                containerColor = GENRE_COLOR[genre]?.color ?: Color.Black,                labelColor = Color.White            ), border = AssistChipDefaults.assistChipBorder(                enabled = true,                borderColor = GENRE_COLOR[genre]?.color ?: Color.Black            )        )    }}@Composableprivate fun AnimeThemeInfo(    title: String?,    songList: List<String?>) {    Column(        modifier = Modifier            .padding(                top = 8.dp,                bottom = 16.dp,                start = 4.dp            )    ) {        Text(            modifier = Modifier                .placeholder(visible = title == null),            text = title ?: UiConst.TITLE_PREVIEW,            fontSize = 14.sp,            fontWeight = FontWeight.SemiBold        )        Spacer(modifier = Modifier.height(8.dp))        songList.forEach { themeTitle ->            Text(                modifier = Modifier                    .placeholder(visible = title == null),                text = themeTitle ?: UiConst.TITLE_PREVIEW,                fontSize = 13.sp            )        }    }}@Composableprivate fun AnimeRelationInfo(    animeList: List<AnimeRelationInfo>,    onClick: (animeInfo: AnimeInfo) -> Unit) {    LazyRow(        modifier = Modifier            .fillMaxWidth()            .wrapContentHeight(),        horizontalArrangement = Arrangement.spacedBy(4.dp),        contentPadding = PaddingValues(            start = 4.dp,            end = 8.dp,            bottom = 4.dp        )    ) {        items(            animeList,            key = { it.animeBasicInfo.id }        ) { animeInfo ->            ItemAnimeSmall(                item = animeInfo.animeBasicInfo            ) {                onClick(animeInfo.animeBasicInfo)            }        }    }}@Composableprivate fun AnimeSummaryInfo(    animeDetailInfo: AnimeDetailInfo?,    onSeasonYearClick: (Int, String) -> Unit,    onStudioClick: (Int) -> Unit) {    Column(        modifier = Modifier            .fillMaxWidth()            .wrapContentHeight()            .padding(                start = 4.dp,                bottom = 16.dp            )    ) {        ItemAnimeInfoNonClick("Romaji", animeDetailInfo?.animeInfo?.title)        ItemAnimeInfoNonClick("English", animeDetailInfo?.titleEnglish)        ItemAnimeInfoNonClick("Native", animeDetailInfo?.titleNative)        ItemAnimeInfoNonClick("Format", animeDetailInfo?.animeInfo?.format)        if (animeDetailInfo?.episode.isNotEmptyValue) {            ItemAnimeInfoNonClick("Episode", "${animeDetailInfo?.episode}Ep")        }        if (animeDetailInfo?.duration.isNotEmptyValue) {            ItemAnimeInfoNonClick("Durations", "${animeDetailInfo?.duration ?: 0}Min")        }        if (animeDetailInfo?.startDate.isNotEmptyValue) {            ItemAnimeInfoNonClick("StartDate", animeDetailInfo?.startDate)        }        if (animeDetailInfo?.endDate.isNotEmptyValue) {            ItemAnimeInfoNonClick("EndDate", animeDetailInfo?.endDate)        }        if (animeDetailInfo != null && animeDetailInfo.animeInfo.seasonYear != 0) {            ItemAnimeInfoClickAble(                "Season",                "${animeDetailInfo.animeInfo.seasonYear} ${animeDetailInfo.animeInfo.season}",            ) {                onSeasonYearClick(                    animeDetailInfo.animeInfo.seasonYear,                    animeDetailInfo.animeInfo.season                )            }        }        if (animeDetailInfo != null && animeDetailInfo.studioInfo?.id.isNotEmptyValue) {            ItemAnimeInfoClickAble(                "Studio",                animeDetailInfo.studioInfo!!.name,            ) {                onStudioClick(                    animeDetailInfo.studioInfo!!.id                )            }        }    }}@Composableprivate fun ItemAnimeTags(    tags: List<TagInfo?>,    onClick: (String) -> Unit,    onLongClick: (String) -> Unit) {    var isShowSpoiler by remember { mutableStateOf(false) }    val unIncludeTags by remember { mutableStateOf(tags.filter { it?.isSpoiler == false }) }    val itemHeight by if (isShowSpoiler) {        if (tags.size % 2 == 0) {            remember { mutableIntStateOf(tags.size * (10 + 4)) }        } else {            remember { mutableIntStateOf((tags.size + 1) * (10 + 4)) }        }    } else {        if (unIncludeTags.size % 2 == 0) {            remember { mutableIntStateOf(unIncludeTags.size * (10 + 4)) }        } else {            remember { mutableIntStateOf((unIncludeTags.size + 1) * (10 + 4)) }        }    }    Column(        modifier = Modifier            .fillMaxWidth()            .padding(                top = 8.dp,                bottom = 16.dp,                start = 4.dp            )            .animateContentSize(                animationSpec = spring(                    dampingRatio = Spring.DampingRatioMediumBouncy,                    stiffness = Spring.StiffnessLow                )            )    ) {        Row(            modifier = Modifier                .fillMaxWidth(),            horizontalArrangement = Arrangement.SpaceBetween        ) {            Text(                modifier = Modifier                    .padding(bottom = 8.dp),                text = "Tags",                fontSize = 14.sp,                fontWeight = FontWeight.SemiBold            )            if (tags.any { it?.isSpoiler == true }) {                TextButton(onClick = { isShowSpoiler = !isShowSpoiler }) {                    Text(                        modifier = Modifier                            .padding(bottom = 8.dp),                        text = if (isShowSpoiler) {                            "Hide Spoiler Tag"                        } else {                            "Show Spoiler Tag"                        },                        fontSize = 14.sp,                        fontWeight = FontWeight.SemiBold                    )                }            }        }        LazyVerticalGrid(            modifier = Modifier                .fillMaxWidth()                .height(itemHeight.dp),            columns = GridCells.Fixed(2),            horizontalArrangement = Arrangement.spacedBy(16.dp)        ) {            if (isShowSpoiler) {                items(tags) { tagInfo ->                    if (tagInfo != null) {                        ItemTagInfo(                            tagInfo = tagInfo,                            onClick = { onClick(it) },                            onLongClick = { onLongClick(it) }                        )                    }                }            } else {                items(unIncludeTags) { tagInfo ->                    if (tagInfo != null) {                        ItemTagInfo(                            tagInfo = tagInfo,                            onClick = { onClick(it) },                            onLongClick = { onLongClick(it) }                        )                    }                }            }        }    }}@OptIn(ExperimentalFoundationApi::class)@Composableprivate fun ItemTagInfo(    tagInfo: TagInfo,    onClick: (String) -> Unit,    onLongClick: (String) -> Unit) {    Row(        modifier = Modifier            .fillMaxWidth()            .combinedClickable(                onClick = {                    onClick(tagInfo.name)                },                onLongClick = {                    if (tagInfo.desc != null) {                        onLongClick(tagInfo.desc!!)                    }                }            ),        horizontalArrangement = Arrangement.SpaceBetween    ) {        Text(            modifier = Modifier                .fillMaxWidth(0.6f),            text = tagInfo.name,            maxLines = 1,            fontSize = 12.sp,            color = if (tagInfo.isSpoiler) Red500 else Color.Black        )        Text(            modifier = Modifier                .fillMaxWidth(0.4f),            text = "${tagInfo.ranking}%",            maxLines = 1,            fontSize = 12.sp,            color = if (tagInfo.isSpoiler) Red500 else Color.Black        )    }}@Composableprivate fun ItemAnimeInfoClickAble(    title: String,    value: String?,    onClick: () -> Unit = { }) {    Row(        modifier = Modifier            .fillMaxWidth()            .padding(horizontal = 4.dp, vertical = 8.dp)    ) {        Text(            text = "$title : ",            fontWeight = FontWeight.Bold,            fontSize = 14.sp,        )        Spacer(modifier = Modifier.weight(1f))        Text(            modifier = Modifier                .clickable { onClick() },            text = value ?: "Unknown",            fontWeight = FontWeight.Bold,            fontSize = 14.sp,            color = Red500        )    }}@Composableprivate fun ItemAnimeInfoNonClick(    title: String,    value: String?) {    Row(        modifier = Modifier            .fillMaxWidth()            .padding(horizontal = 4.dp, vertical = 8.dp)    ) {        Text(            text = "$title : ",            fontWeight = FontWeight.Bold,            fontSize = 14.sp,        )        Spacer(modifier = Modifier.weight(1f))        Text(            text = value ?: "Unknown",            fontWeight = FontWeight.Bold,            fontSize = 14.sp        )    }}@SuppressLint("DefaultLocale")@Composableprivate fun AnimeScoreInfo(animeDetailInfo: AnimeDetailInfo?) {    HorizontalDivider(        modifier = Modifier            .fillMaxWidth()            .padding(top = 8.dp)    )    Row(        modifier = Modifier            .fillMaxWidth()            .wrapContentHeight()            .padding(                top = 16.dp,                bottom = 16.dp            ),        horizontalArrangement = Arrangement.SpaceAround    ) {        val summaryList = listOf(            "Average" to "${animeDetailInfo?.animeInfo?.averageScore ?: 0}%",            "Mean" to "${animeDetailInfo?.meanScore ?: 0}%",            "Popularity" to animeDetailInfo?.popularScore.toCommaFormat,            "Favourites" to animeDetailInfo?.animeInfo?.favourites.toCommaFormat        )        summaryList.forEach {            Column(                horizontalAlignment = Alignment.CenterHorizontally            ) {                Text(                    modifier = Modifier                        .placeholder(visible = animeDetailInfo == null),                    text = it.second,                    fontSize = 14.sp,                    fontWeight = FontWeight.SemiBold                )                Text(                    modifier = Modifier                        .placeholder(visible = animeDetailInfo == null),                    text = it.first,                    fontSize = 14.sp,                    fontWeight = FontWeight.SemiBold                )            }        }    }    HorizontalDivider(        modifier = Modifier            .fillMaxWidth()            .padding(bottom = 8.dp)    )}@Composableprivate fun AnimeDescription(description: String?) {    var expandedDescButton by remember { mutableStateOf(false) }    var isTextOverflow by remember { mutableStateOf(false) }    Column(        modifier = Modifier            .padding(                top = 8.dp,                bottom = 8.dp,                start = 4.dp            )            .animateContentSize(                animationSpec = spring(                    dampingRatio = Spring.DampingRatioMediumBouncy,                    stiffness = Spring.StiffnessLow                )            ),        horizontalAlignment = Alignment.CenterHorizontally    ) {        val spannedText = AnnotatedString.fromHtml(            description ?: stringResource(id = R.string.lorem_ipsum)        )        if (expandedDescButton) {            Text(                modifier = Modifier                    .placeholder(visible = description == null),                text = spannedText,                fontSize = 16.sp            )        } else {            Text(                modifier = Modifier                    .placeholder(visible = description == null),                text = spannedText,                onTextLayout = {                    isTextOverflow = it.hasVisualOverflow                },                fontSize = 16.sp,                maxLines = 5,                overflow = TextOverflow.Ellipsis            )        }        if (isTextOverflow && !expandedDescButton) {            Button(                modifier = Modifier                    .padding(                        top = 8.dp,                        bottom = 8.dp                    ),                onClick = { expandedDescButton = !expandedDescButton }            ) {                Icon(                    imageVector = Icons.Filled.ArrowDownward,                    contentDescription = null                )            }        }    }}