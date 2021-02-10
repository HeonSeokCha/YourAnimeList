package com.chs.youranimelist.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.ActivityMainBinding
import com.chs.youranimelist.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(binding.mainFrameLayout.id,HomeFragment()).commit()
    }

    fun changeFragment(targetFragment: Fragment, id: Int, addToBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val bundle = Bundle().apply {
            this.putInt("id", id)
        }
        targetFragment.arguments = bundle
        fragmentTransaction.replace(binding.mainFrameLayout.id, targetFragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    fun changeFragment(targetFragment: Fragment, sortType: String, addToBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val bundle = Bundle().apply {
            this.putString("sortType", sortType)
        }
        targetFragment.arguments = bundle
        fragmentTransaction.replace(binding.mainFrameLayout.id, targetFragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

}