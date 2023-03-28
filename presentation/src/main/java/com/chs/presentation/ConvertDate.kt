package com.chs.presentation

import java.text.SimpleDateFormat
import java.util.*

object ConvertDate {

    fun convertToDateFormat(year: Int?, month: Int?, day: Int?): String? {

        if (year == null || month == null || day == null) return ""

        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.KOREA)
        return try {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            dateFormat.format(calendar.time)
        } catch (e: Exception) {
            e.toString()
        }
    }

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

    fun getVariationYear(isNext: Boolean): Int {
        val calendar = Calendar.getInstance()
        return if (isNext) {
            if (calendar.get(Calendar.MONTH) in Calendar.OCTOBER..Calendar.DECEMBER) {
                calendar.get(Calendar.YEAR) + 1
            } else calendar.get(Calendar.YEAR)
        } else {
            if (calendar.get(Calendar.MONTH) in Calendar.JANUARY..Calendar.MARCH) {
                calendar.get(Calendar.YEAR) - 1
            } else calendar.get(Calendar.YEAR)
        }
    }

    fun getCurrentYear(): Int = Calendar.getInstance().get(Calendar.YEAR)
}