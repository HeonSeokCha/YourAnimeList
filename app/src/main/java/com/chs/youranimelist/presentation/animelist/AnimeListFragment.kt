package com.chs.youranimelist.presentation.animelist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentAnimeListBinding
import com.chs.youranimelist.presentation.browse.BrowseActivity
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeListFragment : Fragment() {
    private var _binding: FragmentAnimeListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnimeListViewModel by viewModels()

    private lateinit var animeListAdapter: AnimeListAdapter


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
        initRecyclerView()
        getAnimeList()
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        if (::animeListAdapter.isInitialized) {
            viewModel.getAllAnimeList()
        }
    }

    private fun getAnimeList() {
        viewModel.animeListResponse.observe(viewLifecycleOwner) {
            animeListAdapter.submitList(it.toMutableList())
        }
    }

    private fun initRecyclerView() {
        animeListAdapter = AnimeListAdapter { id, idMal ->
            val intent = Intent(requireContext(), BrowseActivity::class.java).apply {
                this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                this.putExtra(Constant.TARGET_ID, id)
                this.putExtra(Constant.TARGET_ID_MAL, idMal)
            }
            startActivity(intent)
        }

        binding.rvAnimeList.apply {
            this.adapter = animeListAdapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_lists, menu)

        val searchItem = menu.findItem(R.id.menu_list_search)
        (searchItem.actionView as SearchView).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                closeKeyboard()
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.searchAnimeList(query!!)
                return false
            }
        })
    }

    private fun closeKeyboard() {
        if (requireActivity().currentFocus != null) {
            val inputMethodManager = requireActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken, 0
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvAnimeList.adapter = null
        _binding = null
    }
}