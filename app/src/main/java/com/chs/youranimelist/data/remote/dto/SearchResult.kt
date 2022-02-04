package com.chs.youranimelist.data.remote.dto

import com.chs.youranimelist.search.SearchAnimeQuery
import com.chs.youranimelist.search.SearchCharacterQuery
import com.chs.youranimelist.search.SearchMangaQuery

class SearchResult(
    var animeSearchResult: SearchAnimeQuery.Medium? = null,
    var mangaSearchResult: SearchMangaQuery.Medium? = null,
    var charactersSearchResult: SearchCharacterQuery.Character? = null
)