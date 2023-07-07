package com.chs.data

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Util {
    fun convertToDateFormat(
        year: Int?,
        month: Int?,
        day: Int?
    ): String {
        if (year == null) return ""

        var datePattern = ""

        if (month != null) {
            datePattern += "MMM"
        }

        if (day != null) {
            datePattern += " dd"
        }

        datePattern += if (month == null && day == null) {
            "yyyy"
        } else {
            ", yyyy"
        }

        val dateFormat = SimpleDateFormat(datePattern, Locale.US)
        return try {
            val calendar = Calendar.getInstance().apply {
                this.set(Calendar.YEAR, year)
                if (month != null) {
                    this.set(Calendar.MONTH, month - 1)
                }
                if (day != null) {
                    this.set(Calendar.DAY_OF_MONTH, day)
                }
            }
            dateFormat.format(calendar.time)
        } catch (e: Exception) {
            ""
        }
    }
}