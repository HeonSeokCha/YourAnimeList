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
                if (year == null) {
                    "0$month"
                } else {
                    " / 0$month"
                }
            } else {
                if (year == null) {
                    "$month"
                } else {
                    " / $month"
                }
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

    fun convertSpoilerSentenceForDesc(
        desc: String,
        list: List<Pair<IntRange, String>>
    ): String {
        var a: String = desc
        var tempIdx: Int = 0
        list.forEach {
            a = a.replaceRange(
                (it.first.first - 2) + (tempIdx * 13)..it.first.last + 2 + (tempIdx * 13),
                "==[isSpoiler](${it.second.replace(" ", "%")})=="
            )
            tempIdx += 1
        }
        return a
    }
}