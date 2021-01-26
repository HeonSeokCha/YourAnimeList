package com.chs.youranimelist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chs.youranimelist.databinding.FragmentTabOverviewBinding
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.viewmodel.MainViewModel


class TabOverviewFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private val repository by lazy { AnimeRepository() }
    private lateinit var binding: FragmentTabOverviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabOverviewBinding.inflate(inflater,container,false)
        viewModel = MainViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        viewModel.getOverView().observe(viewLifecycleOwner,{
            binding.txtAnimeDescription.text = it
        })
    }
}