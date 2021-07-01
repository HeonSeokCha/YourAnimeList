package com.chs.youranimelist.ui.browse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.ActivityBrowseBinding
import com.chs.youranimelist.ui.browse.anime.AnimeDetailFragment
import com.chs.youranimelist.ui.browse.character.CharacterFragment
import com.chs.youranimelist.ui.sortedlist.SortedFragment
import com.chs.youranimelist.util.Constant

class BrowseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBrowseBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNav()
    }

    private fun initNav() {
        val navHostFragment: NavHostFragment = binding.navContainer.getFragment()!!
        navController = navHostFragment.navController
    }
}