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

    fun getCurrentSeason(): Season {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.MONTH)) {
            in Calendar.APRIL..Calendar.JUNE -> Season.SPRING
            in Calendar.JULY..Calendar.SEPTEMBER -> Season.SUMMER
            in Calendar.OCTOBER..Calendar.DECEMBER -> Season.FALL
            in Calendar.JANUARY..Calendar.MARCH -> Season.WINTER
            else -> Season.WINTER
        }
    }

    fun getNextSeason(): Season {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.MONTH)) {
            in Calendar.APRIL..Calendar.JUNE -> Season.SUMMER
            in Calendar.JULY..Calendar.SEPTEMBER -> Season.FALL
            in Calendar.OCTOBER..Calendar.DECEMBER -> Season.WINTER
            in Calendar.JANUARY..Calendar.MARCH -> Season.SPRING
            else -> Season.WINTER
        }
    }

    fun getCurrentYear(upComing: Boolean = false): Int {
        val calendar = Calendar.getInstance()
        return if (upComing) {
            if (calendar.get(Calendar.MONTH) in Calendar.OCTOBER..Calendar.DECEMBER) {
                calendar.get(Calendar.YEAR) + 1
            } else calendar.get(Calendar.YEAR)
        } else {
            calendar.get(Calendar.YEAR)
        }
    }
}