package com.chs.youranimelist.ui.characterlist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.R
import com.chs.youranimelist.data.repository.CharacterListRepository
import com.chs.youranimelist.databinding.FragmentCharacterListBinding
import com.chs.youranimelist.ui.base.BaseFragment
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.search.SearchActivity

class CharacterListFragment : BaseFragment() {
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!
    private val repository by lazy { CharacterListRepository(activity!!.application) }
    private lateinit var viewModel: CharacterListViewModel
    private lateinit var charaListAdapter: CharacterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        viewModel = CharacterListViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getCharaList()
    }

    private fun getCharaList() {
        viewModel.getAllCharaList().observe(viewLifecycleOwner, {
            charaListAdapter.submitList(it)
        })
    }

    private fun initRecyclerView() {
        charaListAdapter = CharacterListAdapter() { id ->
            val intent = Intent(this.context, BrowseActivity::class.java).apply {
                this.putExtra("type", "CHARA")
                this.putExtra("id", id)
            }
            startActivity(intent)
        }
        binding.rvCharaList.apply {
            adapter = charaListAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    override fun onResume() {
        super.onResume()
        if (::charaListAdapter.isInitialized) {
            getCharaList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}