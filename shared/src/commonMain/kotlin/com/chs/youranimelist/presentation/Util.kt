package com.chs.youranimelist.presentation

import java.util.*

object Util {

    fun getCurrentSeason(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.MONTH)) {
            in Calendar.APRIL..Calendar.JUNE -> UiConst.Season.SPRING.rawValue
            in Calendar.JULY..Calendar.SEPTEMBER -> UiConst.Season.SUMMER.rawValue
            in Calendar.OCTOBER..Calendar.DECEMBER -> UiConst.Season.FALL.rawValue
            in Calendar.JANUARY..Calendar.MARCH -> UiConst.Season.WINTER.rawValue
            else -> UiConst.Season.WINTER.rawValue
        }
    }

    fun getNextSeason(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.MONTH)) {
            in Calendar.APRIL..Calendar.JUNE -> UiConst.Season.SUMMER.rawValue
            in Calendar.JULY..Calendar.SEPTEMBER -> UiConst.Season.FALL.rawValue
            in Calendar.OCTOBER..Calendar.DECEMBER -> UiConst.Season.WINTER.rawValue
            in Calendar.JANUARY..Calendar.MARCH -> UiConst.Season.SPRING.rawValue
            else -> UiConst.Season.WINTER.rawValue
        }
    }

    fun getVariationYear(): Int {
        val calendar = Calendar.getInstance()
        return if (calendar.get(Calendar.MONTH) in Calendar.OCTOBER..Calendar.DECEMBER) {
            calendar.get(Calendar.YEAR) + 1
        } else calendar.get(Calendar.YEAR)
    }

    fun getCurrentYear(): Int = Calendar.getInstance().get(Calendar.YEAR)
}