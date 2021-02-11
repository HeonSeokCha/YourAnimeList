package com.chs.youranimelist.ui.browse

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.chs.youranimelist.databinding.ActivityBrowseBinding
import com.chs.youranimelist.ui.browse.anime.AnimeDetailFragment
import com.chs.youranimelist.ui.home.HomeFragment

class BrowseActivity: AppCompatActivity() {
    private lateinit var binding: ActivityBrowseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.window?.apply { this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        changeFragment(AnimeDetailFragment(),
            intent.getIntExtra("id",0),false)
        if(intent?.data != null) {
            when(intent.getStringExtra("browseType")) {
                "ANIME" -> {

                }
            }
        }
    }

    fun changeFragment(targetFragment: Fragment, id: Int, addToBackStack: Boolean) {
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