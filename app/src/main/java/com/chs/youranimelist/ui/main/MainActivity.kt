package com.chs.youranimelist.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.ActivityAnimeListBinding
import com.chs.youranimelist.databinding.ActivityMainBinding
import com.chs.youranimelist.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.window?.apply { this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        initNavigation()
    }

    private fun initNavigation() {
        val fragmentList = listOf(HomeFragment())
        binding.navViewPager.isUserInputEnabled = false
        binding.navViewPager.offscreenPageLimit = fragmentList.size
        binding.navViewPager.adapter = MainViewPagerAdapter(this,fragmentList)
    }

}