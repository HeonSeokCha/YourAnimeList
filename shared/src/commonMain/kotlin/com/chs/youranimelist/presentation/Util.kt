package com.chs.youranimelist.presentation

import com.chs.youranimelist.domain.model.SeasonType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

object Util {

    fun getCurrentSeason(): SeasonType {
        val now: Instant = Clock.System.now()
        val today: LocalDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date

        return when (today.month) {
            in Month.APRIL..Month.JUNE -> SeasonType.SPRING
            in Month.JULY..Month.SEPTEMBER -> SeasonType.SUMMER
            in Month.OCTOBER..Month.DECEMBER -> SeasonType.FALL
            in Month.JANUARY..Month.MARCH -> SeasonType.WINTER
            else -> SeasonType.WINTER
        }
    }

    fun getNextSeason(): SeasonType {
        val now: Instant = Clock.System.now()
        val today: LocalDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        return when (today.month) {
            in Month.APRIL..Month.JUNE -> SeasonType.SUMMER
            in Month.JULY..Month.SEPTEMBER -> SeasonType.FALL
            in Month.OCTOBER..Month.DECEMBER -> SeasonType.WINTER
            in Month.JANUARY..Month.MARCH -> SeasonType.SPRING
            else -> SeasonType.WINTER
        }
    }

    fun getVariationYear(): Int {
        val now: Instant = Clock.System.now()
        val today: LocalDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        return if (today.month in Month.OCTOBER..Month.DECEMBER) today.year + 1 else today.year
    }

    fun getCurrentYear(): Int {
        val now: Instant = Clock.System.now()
        val today: LocalDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        return today.year
    }
}