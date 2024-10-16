package com.chs.data


object Util {
    fun convertToDateFormat(
        year: Int?,
        month: Int?,
        day: Int?
    ): String {

        var datePattern: String = if (year == null) "" else "$year"

        if (month != null) {
            datePattern += if (month < 10) {
                " / 0$month"
            } else {
                " / $month"
            }
        }

        if (day != null) {
            datePattern += if (day < 10) {
                " / 0$day"
            } else {
                " / $day"
            }
        }

        return datePattern
    }

    fun findSpoilerStringList(desc: String): List<Pair<IntRange, String>> {
        val a: ArrayList<Pair<IntRange, String>> = arrayListOf()
        var lastFindIdx: Int = 0
        while (true) {
            if (desc.indexOf(string = "~!", startIndex = lastFindIdx) == -1) break

            val findSpoilerStartIdx: Int = desc.indexOf(string = "~!", startIndex = lastFindIdx) + 2
            val findSpoilerLastIdx: Int =
                desc.indexOf(string = "!~", startIndex = findSpoilerStartIdx) - 1

            a.add(
                findSpoilerStartIdx..findSpoilerLastIdx to
                        desc.substring(findSpoilerStartIdx..findSpoilerLastIdx)
            )

            lastFindIdx = findSpoilerLastIdx
        }
        return a
    }
}