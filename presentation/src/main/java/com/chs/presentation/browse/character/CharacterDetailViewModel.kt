package com.chs.presentation.browse.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.chs.domain.model.CharacterInfo
import com.chs.domain.model.onError
import com.chs.domain.model.onSuccess
import com.chs.presentation.UiConst
import com.chs.domain.usecase.DeleteCharaInfoUseCase
import com.chs.domain.usecase.GetCharaDetailAnimeListUseCase
import com.chs.domain.usecase.GetCharaDetailUseCase
import com.chs.domain.usecase.GetSavedCharaInfoUseCase
import com.chs.domain.usecase.InsertCharaInfoUseCase
import com.chs.presentation.browse.BrowseScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCharaDetailUseCase: GetCharaDetailUseCase,
    private val getCharaAnimeListUseCase: GetCharaDetailAnimeListUseCase,
    private val checkSaveCharaUseCase: GetSavedCharaInfoUseCase,
    private val insertCharaUseCase: InsertCharaInfoUseCase,
    private val deleteCharaUseCase: DeleteCharaInfoUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(CharacterDetailState())
    val state = _state
        .onStart {
            getCharacterDetail(charaId)
            getCharacterDetailAnimeList(charaId)
            isSaveCharacter(charaId)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val charaId: Int = savedStateHandle.toRoute<BrowseScreen.CharacterDetail>().id

    fun changeEvent(event: CharaDetailEvent) {
        when (event) {

            is CharaDetailEvent.InsertCharaInfo -> {
                insertCharacter(event.info)
            }

            is CharaDetailEvent.DeleteCharaInfo -> {
                deleteCharacter(event.info)
            }

            else -> Unit
        }
    }

    private fun getCharacterDetail(charaId: Int) {
        viewModelScope.launch {
            getCharaDetailUseCase(charaId)
                .onSuccess { success ->
                    _state.update {
                        it.copy(
                            isRefresh = false,
                            characterDetailInfo = success
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isRefresh = false,
                            isError = error.message
                        )
                    }
                }
        }
    }

    private fun getCharacterDetailAnimeList(charaId: Int) {
        _state.update {
            it.copy(
                animeList = getCharaAnimeListUseCase(
                    charaId = charaId,
                    sort = UiConst.SortType.POPULARITY.rawValue
                ).cachedIn(viewModelScope)
            )
        }
    }

    private fun isSaveCharacter(charaId: Int) {
        checkSaveCharaUseCase(charaId)
            .onEach { charaInfo ->
                _state.update {
                    it.copy(
                        isSave = charaInfo != null
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun insertCharacter(characterInfo: CharacterInfo?) {
        if (characterInfo != null) {
            viewModelScope.launch {
                insertCharaUseCase(characterInfo)
            }
        }
    }

    private fun deleteCharacter(characterInfo: CharacterInfo?) {
        if (characterInfo != null) {
            viewModelScope.launch {
                deleteCharaUseCase(characterInfo)
            }
        }
    }
}