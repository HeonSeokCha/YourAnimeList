package com.chs.youranimelist.presentation.search

import androidx.lifecycle.ViewModel
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.domain.usecase.SearchAnimeUseCase

class SearchViewModel(
    private val searchAnimeUseCase: SearchAnimeUseCase,
    private val searchMangaQuery: SearchMangaQuery,
    private val searchCharacterQuery: SearchCharacterQuery
) : ViewModel() {

}