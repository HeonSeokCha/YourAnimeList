package presentation.search

sealed class SearchEvent {

    data object Idle : SearchEvent()

    sealed class Click {
        data class Anime(
            val id: Int,
            val idMal: Int
        ) : SearchEvent()

        data class Chara(val id: Int) : SearchEvent()
        data class TabIdx(val idx: Int) : SearchEvent()
    }

    data class OnChangeSearchQuery(val query: String) : SearchEvent()

    data object OnError : SearchEvent()
}