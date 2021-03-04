package com.chs.youranimelist.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.youranimelist.ui.search.anime.SearchAnimeFragment
import com.chs.youranimelist.ui.search.character.SearchCharacterFragment
import com.chs.youranimelist.ui.search.manga.SearchMangaFragment

class SearchViewPagerAdapter(
    fa: FragmentActivity,
    private val searchKeyword: String
) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle().apply {
            this.putString("searchKeyword", searchKeyword)
        }
        return when (position) {
            0 -> SearchAnimeFragment().apply {
                arguments = bundle
            }
            1 -> SearchMangaFragment().apply {
                arguments = bundle
            }
            2 -> SearchCharacterFragment().apply {
                arguments = bundle
            }
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int = 3
}
