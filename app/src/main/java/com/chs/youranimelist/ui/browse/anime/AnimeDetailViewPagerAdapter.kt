package com.chs.youranimelist.ui.browse.anime

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.ui.browse.anime.characters.AnimeCharaFragment
import com.chs.youranimelist.ui.browse.anime.overview.AnimeOverviewFragment

class AnimeDetailViewPagerAdapter(
    fa: FragmentActivity,
    private val AnimeInfo: AnimeDetailQuery.Media): FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AnimeOverviewFragment(AnimeInfo)
            1 -> AnimeCharaFragment(AnimeInfo.characters!!.charactersNode!!)
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int = 3
}