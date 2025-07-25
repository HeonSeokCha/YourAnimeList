package com.chs.youranimelist.presentation.browse.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import app.cash.paging.cachedIn
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.common.onError
import com.chs.common.onSuccess
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.domain.usecase.DeleteCharaInfoUseCase
import com.chs.youranimelist.domain.usecase.GetCharaDetailAnimeListUseCase
import com.chs.youranimelist.domain.usecase.GetCharaDetailUseCase
import com.chs.youranimelist.domain.usecase.GetSavedCharaInfoUseCase
import com.chs.youranimelist.domain.usecase.InsertCharaInfoUseCase
import com.chs.youranimelist.presentation.browse.BrowseScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
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
            CharacterDetailState()
        )

    private val _charaEvent: Channel<CharaDetailEvent> = Channel()
    val charaDetailEvent = _charaEvent.receiveAsFlow()

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
                        it.copy(characterDetailInfo = success)
                    }
                }
                .onError { error ->
                    _charaEvent.send(CharaDetailEvent.OnError)
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
        viewModelScope.launch {
            checkSaveCharaUseCase(charaId).collect { charaInfo ->
                _state.update {
                    it.copy(isSave = charaInfo != null)
                }
            }
        }
    }

    private fun insertCharacter(characterInfo: CharacterInfo) {
        viewModelScope.launch {
            insertCharaUseCase(characterInfo)
        }
    }

    private fun deleteCharacter(characterInfo: CharacterInfo) {
        viewModelScope.launch {
            deleteCharaUseCase(characterInfo)
        }
    }
}