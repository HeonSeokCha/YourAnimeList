package com.chs.youranimelist.ui.search.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentCharacterBinding
import com.chs.youranimelist.network.repository.SearchRepository


class SearchCharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private lateinit var viewModel: SearchCharacterViewModel
    private val repository by lazy { SearchRepository() }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        viewModel = SearchCharacterViewModel(repository)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}