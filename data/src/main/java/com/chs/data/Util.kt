package com.chs.data


object Util {
    fun convertToDateFormat(
        year: Int?,
        month: Int?,
        day: Int?
    ): String {

        var datePattern: String = if (year == null) "" else "$year /"

        if (month != null) {
            datePattern += if (month < 10) {
                "0$month"
            } else {
                "$month"
            }
        }

        if (day != null) {
            datePattern += if (day < 10) {
                "/ 0$day"
            } else {
                "/ $day"
            }
        }

        return datePattern
    }
}