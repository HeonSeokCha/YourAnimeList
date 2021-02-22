package com.chs.youranimelist.ui.browse.anime

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.ui.browse.anime.characters.AnimeCharaFragment
import com.chs.youranimelist.ui.browse.anime.overview.AnimeOverviewFragment
import com.chs.youranimelist.ui.browse.anime.recommend.AnimeRecommendFragment

class AnimeDetailViewPagerAdapter(
    fa: FragmentActivity,
    private val animeId: Int
) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle().apply {
            this.putInt("id", animeId)
        }
        return when (position) {
            0 -> AnimeOverviewFragment().apply {
                arguments = bundle
            }
            1 -> AnimeCharaFragment().apply {
                arguments = bundle
            }
            2 -> AnimeRecommendFragment().apply {
                arguments = bundle
            }
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int = 3
}
