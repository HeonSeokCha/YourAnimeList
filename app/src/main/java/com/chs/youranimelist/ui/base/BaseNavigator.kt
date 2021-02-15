package com.chs.youranimelist.ui.base

import androidx.fragment.app.Fragment

interface BaseNavigator {
    fun changeFragment(type: String, id: Int, addToBackStack: Boolean = true)
}