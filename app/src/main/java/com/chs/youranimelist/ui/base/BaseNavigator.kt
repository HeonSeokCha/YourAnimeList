package com.chs.youranimelist.ui.base

import androidx.fragment.app.Fragment

interface BaseNavigator {
    fun changeFragment(targetFragment: Fragment, id: Int, addToBackStack: Boolean = true)
}