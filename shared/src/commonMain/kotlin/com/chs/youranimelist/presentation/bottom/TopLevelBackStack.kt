package com.chs.youranimelist.presentation.bottom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.chs.youranimelist.presentation.NavDirection
import com.chs.youranimelist.presentation.main.MainScreen

class BottomTopLevelBackStack(startKey: MainScreen) {

    private val tabOrder = listOf(
        MainScreen.Home,
        MainScreen.AnimeList,
        MainScreen.CharaList
    )

    private var topLevelStack: SnapshotStateList<MainScreen> = mutableStateListOf(startKey)
    private val topLevelBackStacks: MutableMap<MainScreen, SnapshotStateList<MainScreen>> =
        mutableMapOf(startKey to mutableStateListOf(startKey))

    var topLevelKey by mutableStateOf(startKey)
        private set

    val backStack: SnapshotStateList<MainScreen> = mutableStateListOf(startKey)

    var direction by mutableStateOf(NavDirection.FORWARD)
        private set

    private fun updateBackStack() {
        backStack.clear()
        backStack.addAll(topLevelBackStacks[topLevelKey] ?: emptyList())
    }

    fun addTopLevel(key: MainScreen) {
        if (key == topLevelKey) return

        val oldIndex = tabOrder.indexOf(topLevelKey)
        val newIndex = tabOrder.indexOf(key)
        direction = if (newIndex >= oldIndex) NavDirection.FORWARD else NavDirection.BACKWARD

        if (topLevelStack.contains(key)) topLevelStack.remove(key)
        topLevelStack.add(key)

        if (topLevelBackStacks[key] == null) {
            topLevelBackStacks[key] = mutableStateListOf(key)
        }

        topLevelKey = key
        updateBackStack()
    }

    fun add(key: MainScreen) {
        direction = NavDirection.FORWARD
        topLevelBackStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast() {
        direction = NavDirection.BACKWARD

        val currentTabStack = topLevelBackStacks[topLevelKey]
        if (currentTabStack != null && currentTabStack.size > 1) {
            currentTabStack.removeAt(currentTabStack.lastIndex)
        } else if (topLevelStack.size > 1) {
            topLevelStack.removeAt(topLevelStack.lastIndex)
            topLevelKey = topLevelStack.last()
        }
        updateBackStack()
    }
}