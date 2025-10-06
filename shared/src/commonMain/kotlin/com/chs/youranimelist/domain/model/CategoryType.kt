package com.chs.youranimelist.domain.model

enum class CategoryType(val title: String) {
    TRENDING("TRENDING NOW"),
    POPULAR("POPULAR THIS SEASON"),
    UPCOMMING("UPCOMING NEXT SEASON"),
    ALLTIME("ALL TIME POPULAR"),
    TOP("TOP ANIME")
}