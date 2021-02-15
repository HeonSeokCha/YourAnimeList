package com.chs.youranimelist.ui.browse

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.chs.youranimelist.databinding.ActivityBrowseBinding
import com.chs.youranimelist.ui.base.BaseActivity
import com.chs.youranimelist.ui.base.BaseNavigator
import com.chs.youranimelist.ui.browse.anime.AnimeDetailFragment
import com.chs.youranimelist.ui.browse.character.CharacterFragment

class BrowseActivity: BaseActivity(), BaseNavigator {
    private lateinit var binding: ActivityBrowseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeFragment(
            intent.getStringExtra("type")!!,
            intent.getIntExtra("id", 0)!!, false)
    }

    override fun changeFragment(type: String ,id: Int,addToBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        lateinit var targetFragment: Fragment
        val bundle = Bundle()
        when(type) {
            "ANIME" -> {
                targetFragment = AnimeDetailFragment()
                bundle.putString("type","ANIME")
                bundle.putInt("id",id)
            }
            "CHARA" -> {
                targetFragment = CharacterFragment()
                bundle.putString("type","CHARA")
                bundle.putInt("id",id)
            }
            "MANGA" -> {

            }
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