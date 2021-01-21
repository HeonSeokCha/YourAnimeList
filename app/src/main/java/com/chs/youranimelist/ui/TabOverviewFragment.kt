package com.chs.youranimelist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentTabOverviewBinding


class TabOverviewFragment : Fragment() {
    private lateinit var binding: FragmentTabOverviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabOverviewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        arguments?.takeIf { it.containsKey("description") }?.apply {
            binding.model = getString("description").toString()
        }
    }
}