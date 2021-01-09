package com.chs.youranimelist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.adapter.AnimeRecListAdapter
import com.chs.youranimelist.adapter.ViewPagerAdapter
import com.chs.youranimelist.databinding.FragmentFirstBinding
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var animeListAdapter: AnimeRecListAdapter
    private val repository by lazy { AnimeRepository() }

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
        getAnimeRecList()
    }

    private fun getAnimeRecList() {
        viewModel.getAnimeRecList().observe(viewLifecycleOwner, {
            lifecycleScope.launchWhenStarted {
                viewModel.netWorkState.collect { netWorkState ->
                    when (netWorkState) {
                        is MainViewModel.NetWorkState.Success -> {
                            animeListAdapter.submitList(it)
                            getPagerAnimeList()
                            binding.mainProgressbar.isVisible = false
                        }
                        is MainViewModel.NetWorkState.Error -> {
                            Toast.makeText(
                                this@FirstFragment.context,
                                netWorkState.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.mainProgressbar.isVisible = false
                        }
                        is MainViewModel.NetWorkState.Loading -> {
                            // if long time loading -> retry? snackBar
                            binding.mainProgressbar.isVisible = true
                        }
                        else -> Unit
                    }
                }
            }
        })
    }

    private fun getPagerAnimeList() {
        viewModel.getPagerAnimeList().observe(viewLifecycleOwner, {
            viewPagerAdapter.submitList(it.media)
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

        binding.rvAnimeRecList.apply {
            animeListAdapter = AnimeRecListAdapter(this@FirstFragment.requireContext(),
                clickListener = { sortType ->
                    val action = FirstFragmentDirections.actionFirstFragmentToAnimeListFragment(sortType)
                    binding.root.findNavController().navigate(action)
            },animeClickListener = { animeId ->
                val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(animeId)
                binding.root.findNavController().navigate(action)
            })
            this.layoutManager = LinearLayoutManager(this@FirstFragment.context)
            this.adapter = animeListAdapter
        }
    }
}