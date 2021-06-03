package com.chs.youranimelist.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.activity.OnBackPressedCallback
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentMainBinding
import com.chs.youranimelist.ui.animelist.AnimeListFragment
import com.chs.youranimelist.ui.characterlist.CharacterListFragment
import com.chs.youranimelist.ui.home.HomeFragment


class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding()
    private lateinit var onBackCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.navViewPager.currentItem != 0) {
                    binding.navViewPager.currentItem = 0
                    binding.bottomNavigationView.selectedItemId = R.id.itemHome
                } else {
                    activity?.finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initNavigation()
    }

    private fun initViewPager() {
        val fragmentList = listOf(
            HomeFragment(),
            AnimeListFragment(),
            CharacterListFragment(),
        )
        binding.navViewPager.isUserInputEnabled = false
        binding.navViewPager.offscreenPageLimit = fragmentList.size
        binding.navViewPager.adapter = MainViewPagerAdapter(this.requireActivity(), fragmentList)
    }

    private fun initNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itemHome -> binding.navViewPager.currentItem = 0
                R.id.itemAnime -> binding.navViewPager.currentItem = 1
                R.id.itemChara -> binding.navViewPager.currentItem = 2
            }
            true
        }
    }

    override fun onDetach() {
        super.onDetach()
        onBackCallback.remove()
    }
}