package com.chs.youranimelist.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
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
import com.chs.youranimelist.ui.main.MainActivity
import com.chs.youranimelist.ui.sortedlist.SortedListActivity
import com.chs.youranimelist.ui.search.SearchActivity

class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private var viewPagerHomeRecAdapter: HomeRecViewPagerAdapter? = null
    private var homeRecListAdapter: HomeRecListParentAdapter? = null
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
        viewModel.getHomeRecList()
        initRecyclerView()
        getAnimeRecList()
        setHasOptionsMenu(true)
    }

    private fun getAnimeRecList() {
        viewModel.animePagerRecommendResponse.observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> binding.mainProgressbar.isVisible = true
                ResponseState.SUCCESS -> {
                    it.data?.viewPager?.media?.forEach { viewPager ->
                        viewModel.pagerRecList.add(viewPager!!)
                    }
                    it.data?.trending?.trendingMedia.apply {
                        val anime: ArrayList<AnimeList> = ArrayList()
                        this!!.forEach { trending ->
                            anime.add(trending!!.fragments.animeList)
                        }
                        viewModel.homeRecList.add(anime)
                    }
                    it.data?.popular?.popularMedia.apply {
                        val anime: ArrayList<AnimeList> = ArrayList()
                        this!!.forEach { popular ->
                            anime.add(popular!!.fragments.animeList)
                        }
                        viewModel.homeRecList.add(anime)
                    }
                    it.data?.upcomming?.upcommingMedia.apply {
                        val anime: ArrayList<AnimeList> = ArrayList()
                        this!!.forEach { upComming ->
                            anime.add(upComming!!.fragments.animeList)
                        }
                        viewModel.homeRecList.add(anime)
                    }
                    it.data?.alltime?.alltimeMedia.apply {
                        val anime: ArrayList<AnimeList> = ArrayList()
                        this!!.forEach { allTime ->
                            anime.add(allTime!!.fragments.animeList)
                        }
                        viewModel.homeRecList.add(anime)
                    }
                    viewPagerHomeRecAdapter!!.notifyDataSetChanged()
                    homeRecListAdapter!!.notifyDataSetChanged()
                    binding.mainProgressbar.isVisible = false
                }
                ResponseState.ERROR -> {
                    binding.mainProgressbar.isVisible = false
                    binding.txtError.apply {
                        this.text = it.message
                        this.isVisible = true
                    }
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.viewPager2.apply {
            viewPagerHomeRecAdapter =
                HomeRecViewPagerAdapter(viewModel.pagerRecList) { id, idMal ->
                    startActivity(Intent(
                        activity!!.applicationContext,
                        BrowseActivity::class.java
                    ).apply {
                        this.putExtra("type", "Media")
                        this.putExtra("id", id)
                        this.putExtra("idMal", idMal)
                    })
                }

            this.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            this.adapter = viewPagerHomeRecAdapter
            binding.indicator.setViewPager2(binding.viewPager2)
        }

        binding.rvAnimeRecList.apply {
            homeRecListAdapter =
                HomeRecListParentAdapter(viewModel.homeRecList, this@HomeFragment.requireContext(),
                    clickListener = { sortType ->
                        val intent = Intent(activity, SortedListActivity::class.java).apply {
                            this.putExtra("sortType", sortType)
                        }
                        startActivity(intent)
                    }, animeClickListener = { id, idMal ->
                        val intent = Intent(activity, BrowseActivity::class.java).apply {
                            this.putExtra("type", "Media")
                            this.putExtra("id", id)
                            this.putExtra("idMal", idMal)
                        }
                        startActivity(intent)
                    }
                )
            this.adapter = homeRecListAdapter
            this.layoutManager = LinearLayoutManager(this@HomeFragment.context)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_home_search -> {
                startActivity(Intent(this@HomeFragment.context, SearchActivity::class.java))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}