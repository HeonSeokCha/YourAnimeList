package com.chs.youranimelist.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.adapter.AnimeListAdapter
import com.chs.youranimelist.adapter.ViewPagerAdapter
import com.chs.youranimelist.databinding.FragmentFirstBinding
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var animeListAdapter: AnimeListAdapter
    private val repository by lazy { AnimeListRepository() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        viewModel = MainViewModel(repository)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getPagerAnimeList()
        test()
    }

    private fun getPagerAnimeList() {
        viewModel.getPagerAnimeList().observe(viewLifecycleOwner, {
            lifecycleScope.launchWhenStarted {
                viewModel.netWorkState.collect { netWorkState ->
                    when (netWorkState) {
                        is MainViewModel.NetWorkState.Success -> {
                            viewPagerAdapter.submitList(it.media)
                            binding.mainProgressbar.isVisible = false
                        }
                        is MainViewModel.NetWorkState.Error -> {
                            Toast.makeText(
                                this@FirstFragment.context,
                                netWorkState.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is MainViewModel.NetWorkState.Loading -> {
                            binding.mainProgressbar.isVisible = true
                        }
                        else -> Unit
                    }
                }
            }
        })
    }

    private fun test() {
        viewModel.getAnimeList().observe(viewLifecycleOwner, {
            lifecycleScope.launchWhenStarted {
                viewModel.netWorkState.collect { netWorkState ->
                    when (netWorkState) {
                        is MainViewModel.NetWorkState.Success -> {
                            animeListAdapter.submitList(it)
                        }
                        is MainViewModel.NetWorkState.Error -> {
                            Toast.makeText(
                                this@FirstFragment.context,
                                netWorkState.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is MainViewModel.NetWorkState.Loading -> {
                            binding.mainProgressbar.isVisible = true
                        }
                        else -> Unit
                    }
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.viewPager2.apply {
            viewPagerAdapter = ViewPagerAdapter(clickListener = { anime ->
                val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(anime.id)
                binding.root.findNavController().navigate(action)
            })
            this.adapter = viewPagerAdapter
        }

        binding.rvAnimeList.apply {
            animeListAdapter = AnimeListAdapter(this@FirstFragment.requireContext(),
                clickListener = {

            },animeclickListener = {
                val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(it)
                binding.root.findNavController().navigate(action)
            })
            this.layoutManager = LinearLayoutManager(this@FirstFragment.context)
            this.adapter = animeListAdapter
        }
    }
}