package com.chs.youranimelist.presentation

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

object Util {

    @OptIn(ExperimentalTime::class)
    fun getCurrentSeason(): String {
        val now: Instant = Clock.System.now()
        val today: LocalDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date

        return when (today.month) {
            in Month.APRIL..Month.JUNE -> UiConst.Season.SPRING.rawValue
            in Month.JULY..Month.SEPTEMBER -> UiConst.Season.SUMMER.rawValue
            in Month.OCTOBER..Month.DECEMBER -> UiConst.Season.FALL.rawValue
            in Month.JANUARY..Month.MARCH -> UiConst.Season.WINTER.rawValue
            else -> UiConst.Season.WINTER.rawValue
        }
    }

    @OptIn(ExperimentalTime::class)
    fun getNextSeason(): String {
        val now: Instant = Clock.System.now()
        val today: LocalDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        return when (today.month) {
            in Month.APRIL..Month.JUNE -> UiConst.Season.SUMMER.rawValue
            in Month.JULY..Month.SEPTEMBER -> UiConst.Season.FALL.rawValue
            in Month.OCTOBER..Month.DECEMBER -> UiConst.Season.WINTER.rawValue
            in Month.JANUARY..Month.MARCH -> UiConst.Season.SPRING.rawValue
            else -> UiConst.Season.WINTER.rawValue
        }
    }

    @OptIn(ExperimentalTime::class)
    fun getVariationYear(): Int {
        val now: Instant = Clock.System.now()
        val today: LocalDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        return if (today.month in Month.OCTOBER..Month.DECEMBER) today.year + 1 else today.year
    }

    @OptIn(ExperimentalTime::class)
    fun getCurrentYear(): Int {
        val now: Instant = Clock.System.now()
        val today: LocalDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        return today.year
    }
}