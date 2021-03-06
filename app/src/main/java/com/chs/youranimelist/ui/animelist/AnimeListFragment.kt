package com.chs.youranimelist.ui.animelist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.R
import com.chs.youranimelist.data.repository.AnimeListRepository
import com.chs.youranimelist.databinding.FragmentAnimeListBinding
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.onQueryTextChanged

class AnimeListFragment : Fragment() {
    private var _binding: FragmentAnimeListBinding? = null
    private val binding get() = _binding!!
    private val repository: AnimeListRepository by lazy { AnimeListRepository(requireActivity().application) }
    private lateinit var viewModel: AnimeListViewModel
    private lateinit var animeListAdapter: AnimeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = AnimeListViewModel(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllAnimeList()
        initRecyclerView()
        getAnimeList()
    }

    override fun onResume() {
        super.onResume()
        if (::animeListAdapter.isInitialized) {
            viewModel.getAllAnimeList()
        }
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
                this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                this.putExtra(Constant.TARGET_ID, id)
                this.putExtra(Constant.TARGET_ID_MAL, idMal)
            }
            startActivity(intent)
        }
        animeListAdapter!!.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.rvAnimeList.apply {
            this.adapter = animeListAdapter
            this.layoutManager = LinearLayoutManager(this.context)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_lists, menu)

        val searchItem = menu.findItem(R.id.menu_list_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            viewModel.searchAnimeList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvAnimeList.adapter = null
        _binding = null
    }
}