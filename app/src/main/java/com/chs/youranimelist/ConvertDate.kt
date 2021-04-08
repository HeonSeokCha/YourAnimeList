package com.chs.youranimelist

import com.chs.youranimelist.type.MediaSeason
import java.text.SimpleDateFormat
import java.util.*

object ConvertDate {

    fun convertToDateFormat(year: Int?, month: Int?, day: Int?): String? {
        if (year == null || month == null || day == null) {
            return "?"
        }
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
        return try {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            dateFormat.format(calendar.time)
        } catch (e: Exception) {
            "?"
        }
    }

    fun getCurrentSeason(): MediaSeason {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.MONTH)) {
            in Calendar.APRIL..Calendar.AUGUST -> MediaSeason.SPRING
            in Calendar.JULY..Calendar.SEPTEMBER -> MediaSeason.SUMMER
            in Calendar.OCTOBER..Calendar.DECEMBER -> MediaSeason.FALL
            in Calendar.JANUARY..Calendar.MARCH -> MediaSeason.WINTER
            else -> MediaSeason.WINTER
        }
    }

    fun getNextSeason(): MediaSeason {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.MONTH)) {
            in Calendar.APRIL..Calendar.AUGUST -> MediaSeason.SUMMER
            in Calendar.JULY..Calendar.SEPTEMBER -> MediaSeason.FALL
            in Calendar.OCTOBER..Calendar.DECEMBER -> MediaSeason.WINTER
            in Calendar.JANUARY..Calendar.MARCH -> MediaSeason.SPRING
            else -> MediaSeason.WINTER
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

    fun Int.secondsToDateTime(): String {
        val dateFormat = SimpleDateFormat("E, dd MMM yyyy, hh:mm a", Locale.US)
        return dateFormat.format(Date(this * 1000L))
    }
}