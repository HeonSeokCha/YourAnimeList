package com.chs.youranimelist.network.response

import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery

class SearchResult(
    var animeSearchResult: SearchAnimeQuery.Medium? = null,
    var mangaSearchResult: SearchMangaQuery.Medium? = null,
    var charactersSearchResult: SearchCharacterQuery.Character? = null
)