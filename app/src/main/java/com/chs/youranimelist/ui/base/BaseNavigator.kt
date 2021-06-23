package com.chs.youranimelist.ui.base

import androidx.fragment.app.Fragment

interface BaseNavigator {
    fun changeFragment(type: String, id: Int, idMal: Int = 0)

    fun changeFragment(genre: String, addToBackStack: Boolean = true)
}