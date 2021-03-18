package com.chs.youranimelist.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchViewPagerAdapter(
    fa: FragmentActivity,
    private val list: List<String>
) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = SearchFragment()
        val bundle: Bundle = Bundle().apply {
            this.putString("searchType", list[position])
        }
        fragment.arguments = bundle
        return fragment
    }

    override fun getItemCount(): Int = list.size
}
