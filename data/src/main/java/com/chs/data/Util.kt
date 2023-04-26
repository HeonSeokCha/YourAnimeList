package com.chs.data

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Util {
    fun convertToDateFormat(year: Int?, month: Int?, day: Int?): String {

        if (year == null || month == null || day == null) return ""

        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
        return try {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            dateFormat.format(calendar.time)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}