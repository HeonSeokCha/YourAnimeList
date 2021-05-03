package com.chs.youranimelist.ui.animelist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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
        initRecyclerView()
        getAnimeList()
    }

    private fun getAnimeList() {
        viewModel.getAllAnimeList().observe(viewLifecycleOwner, {
            animeListAdapter.submitList(it)
            binding.mainAnimeListToolbar.subtitle = "List (${it.size})"
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
        binding.rvAnimeList.apply {
            this.adapter = animeListAdapter
            this.layoutManager = LinearLayoutManager(this.context)
        }
    }

    override fun onResume() {
        super.onResume()
        if (::viewModel.isInitialized && ::animeListAdapter.isInitialized) {
            getAnimeList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}