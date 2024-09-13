package com.chs.presentation

import java.util.*
import com.chs.presentation.UiConst.Season

object Util {

    fun getCurrentSeason(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.MONTH)) {
            in Calendar.APRIL..Calendar.JUNE -> Season.SPRING.rawValue
            in Calendar.JULY..Calendar.SEPTEMBER -> Season.SUMMER.rawValue
            in Calendar.OCTOBER..Calendar.DECEMBER -> Season.FALL.rawValue
            in Calendar.JANUARY..Calendar.MARCH -> Season.WINTER.rawValue
            else -> Season.WINTER.rawValue
        }
    }

    fun getNextSeason(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.MONTH)) {
            in Calendar.APRIL..Calendar.JUNE -> Season.SUMMER.rawValue
            in Calendar.JULY..Calendar.SEPTEMBER -> Season.FALL.rawValue
            in Calendar.OCTOBER..Calendar.DECEMBER -> Season.WINTER.rawValue
            in Calendar.JANUARY..Calendar.MARCH -> Season.SPRING.rawValue
            else -> Season.WINTER.rawValue
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