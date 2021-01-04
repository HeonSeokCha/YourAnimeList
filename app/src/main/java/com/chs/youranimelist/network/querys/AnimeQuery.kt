package com.chs.youranimelist.network.querys

class AnimeQuery {
    fun getAnimeList(
        sort: String = "TRENDING",
        page: Int = 1,perPage: Int = 6): String {
        return """
        query {
          Page(page:$page,perPage:$perPage) {
            media(sort:${sort}_DESC) {
              id
              idMal
              title {
                romaji
                native
                english
              }
              trailer {
                id
                site
                thumbnail
              }
              coverImage {
                extraLarge
                color
              }
              bannerImage
              genres
            }
          }
        }
        """.trimIndent()
    }

    fun getAnimeInfo(animeId: String): String {
        return """
        query {
          Page(page:1,perPage:1) {
            media(id:$animeId) {
              id
              idMal
              title {
                romaji
                native
                english
              }
              description
              format
              trailer {
                id
                thumbnail
              }
              episodes
              coverImage {
                extraLarge
                color
              }
              bannerImage
              genres
              status
              seasonYear
              averageScore
              meanScore
              popularity
              favourites
              source
              recommendations {
                  nodes {
                      mediaRecommendation {
                          id
                          idMal
                          title {
                              romaji
                              english
                      }
                      coverImage {
                          extraLarge
                      }
                  }
                }
              }
            }
          }
        }
    """.trimIndent()
    }
}