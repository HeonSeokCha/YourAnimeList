package com.chs.youranimelist.ui.browse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.chs.youranimelist.databinding.ActivityBrowseBinding
import com.chs.youranimelist.ui.base.BaseNavigator
import com.chs.youranimelist.ui.browse.anime.AnimeDetailFragment
import com.chs.youranimelist.ui.browse.character.CharacterFragment

class BrowseActivity : AppCompatActivity(), BaseNavigator {
    private var _binding: ActivityBrowseBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBrowseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeFragment(
            intent.getStringExtra("type")!!,
            intent.getIntExtra("id", 0),
            intent.getIntExtra("idMal", 0),
            false
        )
    }

    override fun changeFragment(type: String, id: Int, idMal: Int, addToBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        lateinit var targetFragment: Fragment
        val bundle = Bundle()
        when (type) {
            "Media" -> {
                targetFragment = AnimeDetailFragment()
                bundle.putInt("id", id)
                bundle.putInt("idMal", idMal)
            }
            "CHARA" -> {
                targetFragment = CharacterFragment()
                bundle.putInt("id", id)
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}