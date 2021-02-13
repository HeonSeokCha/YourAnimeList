package com.chs.youranimelist.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.chs.youranimelist.databinding.FragmentHomeBinding
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.type.MediaType
import com.chs.youranimelist.ui.base.BaseFragment
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.list.AnimeListActivity
import com.chs.youranimelist.ui.list.AnimeListFragment
import com.chs.youranimelist.ui.main.MainActivity
import com.chs.youranimelist.ui.main.MainViewModel
import kotlinx.coroutines.flow.collect

class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewPagerAnimeRecAdapter: AnimeRecViewPagerAdapter
    private lateinit var animeRecListAdapter: AnimeRecListParentAdapter
    private val animeRepository by lazy { AnimeRepository() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = HomeViewModel(animeRepository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAnimeRecList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun getAnimeRecList() {
        viewModel.getAnimeRecList().observe(viewLifecycleOwner, {
            lifecycleScope.launchWhenStarted {
                viewModel.netWorkState.collect { netWorkState ->
                    when (netWorkState) {
                        is HomeViewModel.NetWorkState.Success -> {
                            getPagerAnimeList()
                            initRecyclerView(it)
                            Log.d("Trending","${it.size}")
                            binding.mainProgressbar.isVisible = false
                        }
                        is HomeViewModel.NetWorkState.Error -> {
                            Toast.makeText(
                                this@HomeFragment.context,
                                netWorkState.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.mainProgressbar.isVisible = false
                        }
                        is HomeViewModel.NetWorkState.Loading -> {
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
            binding.viewPager2.apply {
                viewPagerAnimeRecAdapter = AnimeRecViewPagerAdapter(it,clickListener = { animeId, animeName ->
                    val intent = Intent(activity,BrowseActivity::class.java).apply {
                        this.putExtra("id",animeId)
                        this.putExtra("browseType","ANIME")
                    }
                    startActivity(intent)
                },trailerClickListener = { id ->
                        trailerPlay(id)
                })
                this.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                this.adapter = viewPagerAnimeRecAdapter
                binding.indicator.setViewPager2(binding.viewPager2)
            }
        })
    }

    private fun initRecyclerView(items: List<List<AnimeList>>) {
        binding.rvAnimeRecList.apply {
            animeRecListAdapter = AnimeRecListParentAdapter(items,this@HomeFragment.requireContext(),
                clickListener = { sortType ->
                    val intent = Intent(activity,AnimeListActivity::class.java).apply {
                        this.putExtra("sortType",sortType)
                    }
                    startActivity(intent)
                }, animeClickListener = { animeId ->
                    val intent = Intent(activity,BrowseActivity::class.java).apply {
                        this.putExtra("id",animeId)
                        this.putExtra("browseType","ANIME")
                    }
                    startActivity(intent)
                }
            )
            this.layoutManager = LinearLayoutManager(this@HomeFragment.context)
            this.adapter = animeRecListAdapter
        }
    }

    private fun trailerPlay(videoId: String) {
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(requireActivity(), Uri.parse("https://www.youtube.com/watch?v=$videoId"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}