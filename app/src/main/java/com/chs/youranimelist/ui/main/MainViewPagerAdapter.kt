package com.chs.youranimelist.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPagerAdapter(
    activity: AppCompatActivity,
    private val list: List<Fragment>
    ): FragmentStateAdapter(activity)  {

    override fun createFragment(position: Int): Fragment = list[position]
    override fun getItemCount(): Int = list.size
}