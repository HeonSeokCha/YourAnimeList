package com.chs.youranimelist.ui.characterlist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.R
import com.chs.youranimelist.data.repository.CharacterListRepository
import com.chs.youranimelist.databinding.FragmentCharacterListBinding
import com.chs.youranimelist.ui.animelist.AnimeListViewModel
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.onQueryTextChanged
import kotlinx.coroutines.flow.collectLatest

class CharacterListFragment : Fragment() {
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CharacterListViewModel(requireActivity().application) as T
            }
        }
    }
    private lateinit var charaListAdapter: CharacterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getCharaList()
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        if (::charaListAdapter.isInitialized) {
            viewModel.getAllCharaList()
        }
    }

    private fun getCharaList() {
        viewModel.charaListResponse.observe(viewLifecycleOwner) {
            charaListAdapter.submitList(it)
        }
    }

    private fun initRecyclerView() {
        charaListAdapter = CharacterListAdapter { id ->
            val intent = Intent(requireContext(), BrowseActivity::class.java).apply {
                this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_CHARA)
                this.putExtra(Constant.TARGET_ID, id)
            }
            startActivity(intent)
        }
        charaListAdapter!!.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.rvCharaList.apply {
            this.adapter = charaListAdapter
            this.layoutManager = LinearLayoutManager(this.context)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvCharaList.adapter = null
        _binding = null
    }
}