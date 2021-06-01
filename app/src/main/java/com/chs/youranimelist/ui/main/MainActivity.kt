package com.chs.youranimelist.ui.main

import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.chs.youranimelist.databinding.ActivityMainBinding
import com.chs.youranimelist.ui.base.BaseNavigator
import com.chs.youranimelist.ui.sortedlist.SortedFragment

class MainActivity : AppCompatActivity(), BaseNavigator {
    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.mainHomeToolbar)
        changeFragment("Main", 0, 0, false)
    }

    override fun changeFragment(type: String, id: Int, idMal: Int, addToBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        lateinit var targetFragment: Fragment
        val bundle = Bundle()
        if (type == "Main") {
            targetFragment = MainFragment()
        } else {
            targetFragment = SortedFragment()
            bundle.putString("sortType", type)
        }
        targetFragment.arguments = bundle
        fragmentTransaction.replace(binding.mainContainer.id, targetFragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}