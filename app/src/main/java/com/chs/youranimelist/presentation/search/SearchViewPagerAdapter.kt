package com.chs.youranimelist.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.youranimelist.util.Constant

class SearchViewPagerAdapter(
    parentFragment: Fragment,
    private val list: List<String>
) : FragmentStateAdapter(parentFragment) {
    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = SearchMediaFragment()
        val bundle: Bundle = Bundle().apply {
            when (position) {
                0 -> this.putString(Constant.TARGET_SEARCH, Constant.TARGET_ANIME)
                1 -> this.putString(Constant.TARGET_SEARCH, Constant.TARGET_MANGA)
                2 -> this.putString(Constant.TARGET_SEARCH, Constant.TARGET_CHARA)
            }
        }
        fragment.arguments = bundle
        return fragment
    }

    override fun getItemCount(): Int = list.size
}
