package com.chs.youranimelist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.adapter.CharaAdapter
import com.chs.youranimelist.databinding.FragmentTabCharaBinding
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.viewmodel.MainViewModel

class TabCharaFragment(private val test: List<AnimeDetailQuery.Node>) : Fragment() {
    private lateinit var viewModel: MainViewModel
    private val repository by lazy { AnimeRepository() }
    private lateinit var binding: FragmentTabCharaBinding
    private lateinit var charaAdapter: CharaAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabCharaBinding.inflate(inflater,container,false)
        viewModel = MainViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvAnimeChara.apply {
            charaAdapter = CharaAdapter()
            charaAdapter.submitList(test)
            this.adapter = charaAdapter
            this.layoutManager = GridLayoutManager(this@TabCharaFragment.context,2)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}