package com.chs.youranimelist.ui.characterlist

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.viewbinding.library.fragment.viewBinding
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.R
import com.chs.youranimelist.data.repository.CharacterListRepository
import com.chs.youranimelist.databinding.FragmentCharacterListBinding
import com.chs.youranimelist.ui.base.BaseFragment
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.search.SearchActivity
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.onQueryTextChanged

class CharacterListFragment : BaseFragment(R.layout.fragment_character_list) {
    private val binding: FragmentCharacterListBinding by viewBinding()
    private val repository by lazy { CharacterListRepository(requireActivity().application) }
    private lateinit var viewModel: CharacterListViewModel
    private lateinit var charaListAdapter: CharacterListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = CharacterListViewModel(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getCharaList()
    }

    private fun getCharaList() {
        viewModel.charaListResponse.observe(viewLifecycleOwner, {
            charaListAdapter.submitList(it)
        })
    }

    private fun initRecyclerView() {
        charaListAdapter = CharacterListAdapter() { id ->
            val intent = Intent(this.context, BrowseActivity::class.java).apply {
                this.putExtra("type", Constant.TARGET_CHARA)
                this.putExtra("id", id)
            }
            startActivity(intent)
        }
        binding.rvCharaList.apply {
            adapter = charaListAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_lists, menu)

        val searchItem = menu.findItem(R.id.menu_list_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            viewModel.searchCharaList(it)
        }
    }

    override fun onResume() {
        super.onResume()
        if (::charaListAdapter.isInitialized) {
            viewModel.getAllCharaList()
        }
        setHasOptionsMenu(true)
    }

}