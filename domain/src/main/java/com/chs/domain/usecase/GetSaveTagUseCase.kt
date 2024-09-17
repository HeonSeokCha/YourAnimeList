package com.chs.domain.usecase

import com.chs.domain.model.TagInfo
import com.chs.domain.repository.AnimeRepository
import javax.inject.Inject

class GetSaveTagUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(): List<TagInfo> {
        return repository.getSavedTagList()
    }
}