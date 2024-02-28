package com.chs.data


object Util {
    fun convertToDateFormat(
        year: Int?,
        month: Int?,
        day: Int?
    ): String {

        if (year == null) return ""

        var datePattern: String = ""

        datePattern += "$year"

        if (month != null) {
            if (month < 10) {
                datePattern += "-0$month"
            } else {
                datePattern += "-$month"
            }
        }

        if (day != null) {
            if (day < 10) {
                datePattern += "-0$day"
            } else {
                datePattern += "-$day"
            }
        }

        return datePattern
    }
}