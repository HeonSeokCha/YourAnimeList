package com.chs.youranimelist.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.ActivityMainBinding
import com.chs.youranimelist.ui.animelist.AnimeListFragment
import com.chs.youranimelist.ui.characterlist.CharacterListFragment
import com.chs.youranimelist.ui.home.HomeFragment
import com.chs.youranimelist.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPager()
        initNavigation()
        setSupportActionBar(binding.mainHomeToolbar)
        supportActionBar
    }

    private fun initViewPager() {
        val fragmentList = listOf(
            HomeFragment(),
            AnimeListFragment(),
            CharacterListFragment(),
        )
        binding.navViewPager.isUserInputEnabled = false
        binding.navViewPager.offscreenPageLimit = fragmentList.size
        binding.navViewPager.adapter = MainViewPagerAdapter(this, fragmentList)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}