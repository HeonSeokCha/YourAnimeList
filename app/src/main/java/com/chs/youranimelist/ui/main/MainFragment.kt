package com.chs.youranimelist.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentMainBinding
import com.chs.youranimelist.ui.animelist.AnimeListFragment
import com.chs.youranimelist.ui.characterlist.CharacterListFragment
import com.chs.youranimelist.ui.home.HomeFragment


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var onBackCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initNavigation()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.navViewPager.currentItem != 0) {
                    binding.navViewPager.currentItem = 0
                } else {
                    activity?.finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackCallback)
    }

    private fun initViewPager() {
        val fragmentList = listOf(
            HomeFragment(),
            AnimeListFragment(),
            CharacterListFragment(),
        )
        binding.navViewPager.isUserInputEnabled = false
        binding.navViewPager.offscreenPageLimit = fragmentList.size
        binding.navViewPager.adapter = MainViewPagerAdapter(this.activity!!, fragmentList)
    }

    private fun initNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itemHome -> binding.navViewPager.currentItem = 0
                R.id.itemAnime -> binding.navViewPager.currentItem = 1
                R.id.itemChara -> binding.navViewPager.currentItem = 2
            }
            true
        }
    }

    override fun onDetach() {
        super.onDetach()
        onBackCallback.remove()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}