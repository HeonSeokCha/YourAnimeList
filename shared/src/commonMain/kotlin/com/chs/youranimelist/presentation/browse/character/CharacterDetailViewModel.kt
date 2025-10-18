package com.chs.youranimelist.presentation.browse.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.youranimelist.data.type.Character
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.model.SortType
import com.chs.youranimelist.util.onError
import com.chs.youranimelist.util.onSuccess
import com.chs.youranimelist.domain.usecase.DeleteCharaInfoUseCase
import com.chs.youranimelist.domain.usecase.GetCharaDetailAnimeListUseCase
import com.chs.youranimelist.domain.usecase.GetCharaDetailUseCase
import com.chs.youranimelist.domain.usecase.GetSavedCharaInfoUseCase
import com.chs.youranimelist.domain.usecase.InsertCharaInfoUseCase
import com.chs.youranimelist.presentation.browse.BrowseScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    savedStateHandle: SavedStateHandle,
    getCharaAnimeListUseCase: GetCharaDetailAnimeListUseCase,
    private val getCharaDetailUseCase: GetCharaDetailUseCase,
    private val checkSaveCharaUseCase: GetSavedCharaInfoUseCase,
    private val insertCharaUseCase: InsertCharaInfoUseCase,
    private val deleteCharaUseCase: DeleteCharaInfoUseCase
) : ViewModel() {

    private val charaId: Int = savedStateHandle.toRoute<BrowseScreen.CharacterDetail>().id

    private var _state = MutableStateFlow(CharacterDetailState())
    val state = _state
        .onStart {
            getCharacterDetail(charaId)
            isSaveCharacter(charaId)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _effect: Channel<CharacterDetailEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    val pagingItem: Flow<PagingData<AnimeInfo>> = getCharaAnimeListUseCase(
        charaId = charaId,
        sort = SortType.POPULARITY.rawValue
    ).cachedIn(viewModelScope)

    fun handleIntent(intent: CharaDetailIntent) {
        when (intent) {
            is CharaDetailIntent.ClickSaved -> handleSavedChara(intent.info)
            is CharaDetailIntent.ClickAnime -> {
                _effect.trySend(
                    CharacterDetailEffect.NavigateAnimeDetail(
                        id = intent.id,
                        idMal = intent.idMal
                    )
                )
            }

            is CharaDetailIntent.ClickChara -> {
                _effect.trySend(CharacterDetailEffect.NavigateCharaDetail(id = intent.id))
            }

            is CharaDetailIntent.ClickLink -> {
                _effect.trySend(CharacterDetailEffect.NavigateBrowser(intent.url))
            }

            is CharaDetailIntent.ClickVoiceActor -> {
                _effect.trySend(CharacterDetailEffect.NavigateVoiceActor(id = intent.id))
            }

            is CharaDetailIntent.ClickSpoiler -> {
                _state.update { it.copy(isShowDialog = true, spoilerDesc = intent.desc) }
            }

            CharaDetailIntent.ClickClose -> _effect.trySend(CharacterDetailEffect.NavigateClose)
            CharaDetailIntent.ClickExpand -> _state.update { it.copy(isDescExpand = !it.isDescExpand) }
            CharaDetailIntent.ClickDialogConfirm -> {
                _state.update { it.copy(isShowDialog = false, spoilerDesc = "") }
            }

            CharaDetailIntent.OnAppendLoad -> _state.update { it.copy(isAnimeListAppendLoading = true) }
            CharaDetailIntent.OnAppendLoadComplete -> _state.update {
                it.copy(
                    isAnimeListAppendLoading = false
                )
            }

            CharaDetailIntent.OnLoad -> _state.update { it.copy(isAnimeListLoading = true) }
            CharaDetailIntent.OnLoadComplete -> _state.update { it.copy(isAnimeListLoading = false) }
            CharaDetailIntent.OnError -> {}
        }
    }

    private fun getCharacterDetail(charaId: Int) {
        viewModelScope.launch {
            getCharaDetailUseCase(charaId)
                .onSuccess { info ->
                    _state.update {
                        it.copy(characterDetailInfo = info)
                    }
                }
                .onError { error ->
                    Unit
                }
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

    private fun handleSavedChara(info: CharacterInfo) {
        viewModelScope.launch {
            if (_state.value.isSave) {
                deleteCharaUseCase(info)
            } else {
                insertCharaUseCase(info)
            }
        }
    }
}