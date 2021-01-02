package com.chs.youranimelist.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.chs.youranimelist.R
import com.chs.youranimelist.adapter.ViewPagerAdapter
import com.chs.youranimelist.databinding.FragmentFirstBinding
import com.chs.youranimelist.network.api.AnimeService
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.network.services.RetrofitInstance
import com.chs.youranimelist.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val repository by lazy { AnimeListRepository() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        viewModel = MainViewModel(repository)
        initRecyclerView()
        getPagerAnimeList()
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun getPagerAnimeList() {
        viewModel.getPagerAnimeList().observe(viewLifecycleOwner,{
            viewPagerAdapter.submitList(it.data.page.media)
        })
        binding.mainProgressbar.visibility = View.GONE
    }

    private fun initRecyclerView() {

        binding.viewPager2.apply {
            viewPagerAdapter = ViewPagerAdapter(clickListener = { anime ->
                val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(anime)
                binding.root.findNavController().navigate(action)
            })
            this.adapter = viewPagerAdapter
        }
    }
}