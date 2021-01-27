package com.chs.youranimelist.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.ui.TabCharaFragment
import com.chs.youranimelist.ui.TabOverviewFragment

class ViewPagerAnimeDetailAdapter(
    fm: FragmentActivity,
    private val AnimeInfo: AnimeDetailQuery.Media): FragmentStateAdapter(fm) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> TabOverviewFragment(AnimeInfo)
            1 -> TabCharaFragment(AnimeInfo.characters!!.nodes as List<AnimeDetailQuery.Node>)
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int = 3
}
