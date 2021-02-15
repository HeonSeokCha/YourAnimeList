package com.chs.youranimelist.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.chs.youranimelist.databinding.ActivityMainBinding
import com.chs.youranimelist.ui.base.BaseActivity
import com.chs.youranimelist.ui.base.BaseNavigator
import com.chs.youranimelist.ui.home.HomeFragment

class MainActivity : BaseActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
    }

    private fun initNavigation() {
        val fragmentList = listOf(HomeFragment())
        binding.navViewPager.isUserInputEnabled = false
        binding.navViewPager.offscreenPageLimit = fragmentList.size
        binding.navViewPager.adapter = MainViewPagerAdapter(this,fragmentList)
    }
}