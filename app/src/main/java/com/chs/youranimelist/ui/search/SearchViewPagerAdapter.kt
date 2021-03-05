package com.chs.youranimelist.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.youranimelist.ui.search.anime.SearchAnimeFragment
import com.chs.youranimelist.ui.search.character.SearchCharacterFragment
import com.chs.youranimelist.ui.search.manga.SearchMangaFragment

class SearchViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchAnimeFragment()
            1 -> SearchMangaFragment()
            2 -> SearchCharacterFragment()
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int = 3
}
