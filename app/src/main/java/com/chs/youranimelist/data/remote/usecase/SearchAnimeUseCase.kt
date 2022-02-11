package com.chs.youranimelist.data.remote.usecase

import com.chs.youranimelist.data.remote.repository.SearchRepository
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val repository: SearchRepository
) {
}