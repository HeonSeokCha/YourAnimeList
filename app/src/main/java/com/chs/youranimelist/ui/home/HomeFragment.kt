package com.chs.youranimelist.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentHomeBinding
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.ui.base.BaseFragment
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.list.AnimeListActivity
import com.chs.youranimelist.ui.search.SearchActivity

class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewPagerHomeRecAdapter: HomeRecViewPagerAdapter
    private lateinit var homeRecListAdapter: HomeRecListParentAdapter
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
        initMenu()
    }

    private fun getAnimeRecList() {
        viewModel.getAnimeRecList().observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> binding.mainProgressbar.isVisible = true
                ResponseState.SUCCESS -> {
                    getPagerAnimeList()
                    initRecyclerView(it.data!!)
                    binding.mainProgressbar.isVisible = false
                }
                ResponseState.ERROR -> {
                    binding.mainProgressbar.isVisible = false
                    Toast.makeText(
                        this@HomeFragment.context,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun initMenu() {
        binding.mainHomeToolbar.apply {
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_home_search -> {
                        startActivity(Intent(this@HomeFragment.context, SearchActivity::class.java))
                        true
                    }
                    else -> {
                        super.onOptionsItemSelected(it)
                    }
                }
            }
        }
    }

    private fun getPagerAnimeList() {
        viewModel.getPagerAnimeList().observe(viewLifecycleOwner, {
            binding.viewPager2.apply {
                viewPagerHomeRecAdapter =
                    HomeRecViewPagerAdapter(it, clickListener = { animeId ->
                        val intent = Intent(activity, BrowseActivity::class.java).apply {
                            this.putExtra("type", "ANIME")
                            this.putExtra("id", animeId)
                        }
                        startActivity(intent)
                    })
                this.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                this.adapter = viewPagerHomeRecAdapter
                binding.indicator.setViewPager2(binding.viewPager2)
            }
        })
    }

    private fun initRecyclerView(items: List<List<AnimeList>>) {
        binding.rvAnimeRecList.apply {
            homeRecListAdapter =
                HomeRecListParentAdapter(items, this@HomeFragment.requireContext(),
                    clickListener = { sortType ->
                        val intent = Intent(activity, AnimeListActivity::class.java).apply {
                            this.putExtra("sortType", sortType)
                        }
                        startActivity(intent)
                    }, animeClickListener = { animeId ->
                        val intent = Intent(activity, BrowseActivity::class.java).apply {
                            this.putExtra("type", "ANIME")
                            this.putExtra("id", animeId)
                        }
                        startActivity(intent)
                    }
                )
            this.layoutManager = LinearLayoutManager(this@HomeFragment.context)
            this.adapter = homeRecListAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}