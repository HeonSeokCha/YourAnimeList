package com.chs.youranimelist.ui.detail.anime

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.ui.detail.anime.characters.AnimeCharaFragment
import com.chs.youranimelist.ui.detail.anime.overview.AnimeOverviewFragment

class AnimeDetailViewPagerAdapter(
    fm: FragmentActivity,
    private val AnimeInfo: AnimeDetailQuery.Media): FragmentStateAdapter(fm) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AnimeOverviewFragment(AnimeInfo)
            1 -> AnimeCharaFragment(AnimeInfo.characters!!.nodes as List<AnimeDetailQuery.Node>)
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int = 3
}
