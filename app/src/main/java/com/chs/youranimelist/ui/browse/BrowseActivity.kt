package com.chs.youranimelist.ui.browse

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.chs.youranimelist.databinding.ActivityBrowseBinding
import com.chs.youranimelist.ui.base.BaseActivity
import com.chs.youranimelist.ui.base.BaseNavigator
import com.chs.youranimelist.ui.browse.anime.AnimeDetailFragment

class BrowseActivity: BaseActivity(), BaseNavigator {
    private lateinit var binding: ActivityBrowseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when(intent.getStringExtra("browseType")) {
            "ANIME" -> {
                changeFragment(AnimeDetailFragment(),
                    intent.getIntExtra("id",0),false)
            }
        }
    }

    override fun changeFragment(targetFragment: Fragment, id: Int,addToBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val bundle = Bundle().apply {
            this.putInt("id", id)
        }
        targetFragment.arguments = bundle
        fragmentTransaction.replace(binding.browseFrameLayout.id, targetFragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }
}