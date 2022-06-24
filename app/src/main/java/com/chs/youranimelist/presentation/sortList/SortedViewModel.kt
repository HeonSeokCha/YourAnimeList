package com.chs.youranimelist.presentation.sortList

import androidx.lifecycle.ViewModel
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort

class SortedViewModel : ViewModel() {
    var selectedYear: Int? = null
    var selectedSeason: MediaSeason? = null
    var selectedSort: MediaSort? = null
    var selectGenre: String? = null
}