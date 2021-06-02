package com.chs.youranimelist.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.youranimelist.util.Constant

class SearchViewPagerAdapter(
    fa: FragmentActivity,
    private val list: List<String>
) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = SearchFragment()
        val bundle: Bundle = Bundle().apply {
            this.putString(Constant.TARGET_SEARCH, list[position])
        }
        fragment.arguments = bundle
        return fragment
    }

    override fun getItemCount(): Int = list.size
}
