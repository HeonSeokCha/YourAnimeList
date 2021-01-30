package com.chs.youranimelist.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.FragmentHomeBinding
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.flow.collect

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewPagerAnimeRecAdapter: AnimeRecViewPagerAdapter
    private lateinit var animeRecListAdapter: AnimeRecListParentAdapter
    private lateinit var trailerId: String
    var currentVisiblePosition: Int = 0
    private val repository by lazy { AnimeRepository() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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
                            animeRecListAdapter.submitList(it)
                            getPagerAnimeList()
                            binding.mainProgressbar.isVisible = false
                        }
                        is MainViewModel.NetWorkState.Error -> {
                            Toast.makeText(
                                this@HomeFragment.context,
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
            viewPagerAnimeRecAdapter.submitList(it.media)
        })
    }

    private fun initRecyclerView() {
        binding.viewPager2.apply {
            viewPagerAnimeRecAdapter = AnimeRecViewPagerAdapter(clickListener = { animeId, animeName ->
                val action = HomeFragmentDirections.actionFirstFragmentToSecondFragment(
                    animeId,
                    animeName
                )
                binding.root.findNavController().navigate(action)
            },
            trailerClickListener = { id ->
                trailerPlay(id)
            })
            this.adapter = viewPagerAnimeRecAdapter
        }

        binding.rvAnimeRecList.apply {
            animeRecListAdapter = AnimeRecListParentAdapter(this@HomeFragment.requireContext(),
                clickListener = { sortType ->
                    val action = HomeFragmentDirections.actionFirstFragmentToAnimeListFragment(
                        sortType
                    )
                    binding.root.findNavController().navigate(action)
                }, animeClickListener = { animeId, animeName ->
                    val action = HomeFragmentDirections.actionFirstFragmentToSecondFragment(
                        animeId,
                        animeName
                    )
                    binding.root.findNavController().navigate(action)
                }).apply {
                this.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
            this.layoutManager = LinearLayoutManager(this@HomeFragment.context)
            this.adapter = animeRecListAdapter
        }
    }

    private fun trailerPlay(videoId: String) {
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(requireActivity(), Uri.parse("https://www.youtube.com/watch?v=$videoId"))
    }
}