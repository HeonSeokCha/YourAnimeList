package com.chs.youranimelist.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.chs.youranimelist.databinding.ActivityMainBinding
import com.chs.youranimelist.ui.base.BaseNavigator
import com.chs.youranimelist.ui.sortedlist.SortedFragment
import com.chs.youranimelist.util.Constant

class MainActivity : AppCompatActivity(), BaseNavigator {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.mainHomeToolbar)
        changeFragment(Constant.TARGET_MAIN, 0, 0)
    }

    override fun changeFragment(type: String, id: Int, idMal: Int, addToBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        lateinit var targetFragment: Fragment
        val bundle = Bundle()
        if (type == Constant.TARGET_MAIN) {
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

    override fun changeFragment(genre: String, addToBackStack: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }
}