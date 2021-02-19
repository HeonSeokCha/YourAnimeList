package com.chs.youranimelist.ui.browse.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.databinding.FragmentCharacterBinding
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.CharacterRepository
import kotlinx.coroutines.flow.collect

class CharacterFragment : Fragment() {
    private val repository by lazy { CharacterRepository() }
    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CharacterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        viewModel = CharacterViewModel(repository)
        initView()
        return binding.root
    }

    private fun initView() {
        getCharaInfo(requireArguments().getInt("id", 0).toInput())
    }

    private fun getCharaInfo(charaId: Input<Int>) {
        viewModel.getCharaInfo(charaId).observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> { }
                ResponseState.SUCCESS -> binding.model = it.data
                ResponseState.ERROR -> {
                    Toast.makeText(
                        this@CharacterFragment.context,
                        it.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}