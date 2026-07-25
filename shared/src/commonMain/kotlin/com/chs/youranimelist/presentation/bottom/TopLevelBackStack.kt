package com.chs.youranimelist.presentation.bottom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList

class TopLevelBackStack<T : Any>(startKey: T) {

    private var topLevelStack: SnapshotStateList<T> = mutableStateListOf(startKey)

    private val topLevelBackStacks: MutableMap<T, SnapshotStateList<T>> =
        mutableMapOf(startKey to mutableStateListOf(startKey))

    var topLevelKey by mutableStateOf(startKey)
        private set

    val backStack: SnapshotStateList<T> = mutableStateListOf(startKey)

    private fun updateBackStack() {
        backStack.clear()
        backStack.addAll(topLevelBackStacks[topLevelKey] ?: emptyList())
    }

    fun addTopLevel(key: T) {
        if (key == topLevelKey) return

        if (topLevelStack.contains(key)) topLevelStack.remove(key)

        topLevelStack.add(key)

        if (topLevelBackStacks[key] == null) {
            topLevelBackStacks[key] = mutableStateListOf(key)
        }

        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T) {
        topLevelBackStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast() {
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
