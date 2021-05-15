package com.chs.youranimelist.ui.animelist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.R
import com.chs.youranimelist.data.repository.AnimeListRepository
import com.chs.youranimelist.databinding.FragmentAnimeListBinding
import com.chs.youranimelist.ui.base.BaseFragment
import com.chs.youranimelist.ui.browse.BrowseActivity

class AnimeListFragment : BaseFragment() {
    private var _binding: FragmentAnimeListBinding? = null
    private val binding get() = _binding!!
    private val repository: AnimeListRepository by lazy { AnimeListRepository(activity!!.application) }
    private lateinit var viewModel: AnimeListViewModel
    private lateinit var animeListAdapter: AnimeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeListBinding.inflate(inflater, container, false)
        viewModel = AnimeListViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllAnimeList()
        initRecyclerView()
        getAnimeList()
        setHasOptionsMenu(true)
    }

    private fun getAnimeList() {
        viewModel.animeListResponse.observe(viewLifecycleOwner, {
            animeListAdapter.submitList(it)
        })
    }

    private fun initRecyclerView() {
        animeListAdapter = AnimeListAdapter() { id, idMal ->
            val intent = Intent(this.context, BrowseActivity::class.java).apply {
                this.putExtra("type", "Media")
                this.putExtra("id", id)
                this.putExtra("idMal", idMal)
            }
            startActivity(intent)
        }
        animeListAdapter.setHasStableIds(true)
        binding.rvAnimeList.apply {
            this.adapter = animeListAdapter
            this.layoutManager = LinearLayoutManager(this.context)
        }
    }

    override fun onResume() {
        super.onResume()
        if (::animeListAdapter.isInitialized) {
            viewModel.getAllAnimeList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}