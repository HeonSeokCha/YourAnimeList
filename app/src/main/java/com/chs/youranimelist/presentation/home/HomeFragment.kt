package com.chs.youranimelist.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentHomeBinding
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.presentation.browse.BrowseActivity
import com.chs.youranimelist.util.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private var viewPagerHomeRecAdapter: HomeRecViewPagerAdapter? = null
    private var homeRecListAdapter: HomeRecListParentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
        viewModel.homeRecommendResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkState.Loading -> {
                    binding.layoutShimmerHome.root.isVisible = true
                }

                is NetworkState.Success -> {
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
                    it.data?.upComming?.upCommingMedia.apply {
                        val anime: ArrayList<AnimeList> = ArrayList()
                        this!!.forEach { upComming ->
                            anime.add(upComming!!.fragments.animeList)
                        }
                        viewModel.homeRecList.add(anime)
                    }
                    it.data?.allTime?.allTimeMedia.apply {
                        val anime: ArrayList<AnimeList> = ArrayList()
                        this!!.forEach { allTime ->
                            anime.add(allTime!!.fragments.animeList)
                        }
                        viewModel.homeRecList.add(anime)
                    }
                    viewPagerHomeRecAdapter!!.notifyDataSetChanged()
                    homeRecListAdapter!!.notifyDataSetChanged()
                    binding.layoutShimmerHome.root.isVisible = false
                    binding.indicator.isVisible = true
                    binding.rvAnimeRecList.isVisible = true
                    binding.viewPager2.isVisible = true
                }
                is NetworkState.Error -> {
                    binding.layoutShimmerHome.root.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.viewPager2.apply {
            viewPagerHomeRecAdapter =
                HomeRecViewPagerAdapter(viewModel.pagerRecList) { id, idMal ->
                    startActivity(Intent(
                        requireActivity(),
                        BrowseActivity::class.java
                    ).apply {
                        this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                        this.putExtra(Constant.TARGET_ID, id)
                        this.putExtra(Constant.TARGET_ID_MAL, idMal)
                    })
                }

            this.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            this.adapter = viewPagerHomeRecAdapter
            binding.indicator.setViewPager2(binding.viewPager2)
        }

        binding.rvAnimeRecList.apply {
            homeRecListAdapter =
                HomeRecListParentAdapter(viewModel.homeRecList, requireContext(),
                    object : HomeRecListParentAdapter.HomeRecListener {
                        override fun clickMore(sortType: String) {
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToSortedFragment(sortType)
                            findNavController().navigate(action)
                        }

                        override fun clickAnime(id: Int, idMal: Int) {
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    BrowseActivity::class.java
                                ).apply {
                                    this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                                    this.putExtra(Constant.TARGET_ID, id)
                                    this.putExtra(Constant.TARGET_ID_MAL, idMal)
                                })
                        }
                    })
            this.adapter = homeRecListAdapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_home_search -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                )
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.homeRecList.clear()
        viewModel.pagerRecList.clear()
        binding.rvAnimeRecList.adapter = null
        _binding = null
    }
}