package com.chs.youranimelist.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.youranimelist.ui.BlankFragment
import com.chs.youranimelist.ui.TabCharaFragment
import com.chs.youranimelist.ui.TabOverviewFragment

class ViewPagerAnimeDetailAdapter(fm: FragmentActivity): FragmentStateAdapter(fm) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> TabOverviewFragment()
            1 -> TabCharaFragment()
            else -> BlankFragment()
        }
    }

    override fun getItemCount(): Int = 3
}
