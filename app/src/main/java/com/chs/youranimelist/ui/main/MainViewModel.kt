package com.chs.youranimelist.ui.main

import androidx.lifecycle.*
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeRecListQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel (private val repository: AnimeRepository): ViewModel() {

}