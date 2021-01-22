package com.chs.youranimelist.adapter

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.ui.TabCharaFragment
import com.chs.youranimelist.ui.TabOverviewFragment
import java.io.Serializable
import java.util.ArrayList

class ViewPagerAnimeDetailAdapter(
    fm: FragmentActivity,
    private val AnimeInfo: AnimeDetailQuery.Media): FragmentStateAdapter(fm) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> TabOverviewFragment().apply {
                arguments = Bundle().apply {
                    putString("description","${AnimeInfo.description}")
                }
            }
            1 -> TabCharaFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("chara",AnimeInfo.characters?.nodes as Serializable)
                }
            }
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int = 3
}
