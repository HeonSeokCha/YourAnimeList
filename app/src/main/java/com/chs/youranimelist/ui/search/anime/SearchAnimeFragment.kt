package com.chs.youranimelist.ui.search.anime

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentSearchAnimeBinding
import com.chs.youranimelist.network.repository.SearchRepository
import com.chs.youranimelist.ui.browse.BrowseActivity

class SearchAnimeFragment : Fragment() {
    private var _binding: FragmentSearchAnimeBinding? = null
    private lateinit var viewModel: SearchAnimeViewModel
    private lateinit var searchAnimeAdapter: SearchAnimeAdapter
    private val repository by lazy { SearchRepository() }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchAnimeBinding.inflate(inflater, container, false)
        viewModel = SearchAnimeViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        arguments
        initObserver(arguments?.getString("searchKeyword")!!)
    }

    private fun initObserver(searchKeyword: String) {
        viewModel.getAnimeSearch(searchKeyword = searchKeyword).observe(viewLifecycleOwner, {
            searchAnimeAdapter.submitList(it.data)
        })
    }

    private fun initRecyclerView() {
        binding.rvSearchAnime.apply {
            searchAnimeAdapter = SearchAnimeAdapter { id ->
                val intent = Intent(this.context, BrowseActivity::class.java).apply {
                    this.putExtra("type", "ANIME")
                    this.putExtra("id", id)
                }
                startActivity(intent)
            }
            searchAnimeAdapter.setHasStableIds(true)
            this.layoutManager = LinearLayoutManager(this.context)
            this.adapter = searchAnimeAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}