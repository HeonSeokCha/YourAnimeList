package com.chs.youranimelist.ui.browse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.ActivityBrowseBinding
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
        val navGraph = navController.navInflater.inflate(R.navigation.nav_browse)
        val bundle = Bundle()
        if (intent?.getStringExtra("type") == Constant.TARGET_MEDIA) {
            navGraph.setStartDestination(R.id.animeDetailFragment)
            bundle.apply {
                this.putInt("id", intent?.getIntExtra("id", 0)!!)
                this.putInt("idMal", intent?.getIntExtra("idMal", 0)!!)
            }
        } else {
            navGraph.setStartDestination(R.id.characterFragment)
            bundle.apply {
                this.putInt("id", intent?.getIntExtra("id", 0)!!)
            }
        }
        navController.setGraph(navGraph, bundle)
    }
}