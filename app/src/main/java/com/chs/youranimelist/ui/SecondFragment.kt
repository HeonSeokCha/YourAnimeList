package com.chs.youranimelist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentAnimeDetailBinding

class SecondFragment : Fragment() {
    private lateinit var binding:FragmentAnimeDetailBinding
    val args: SecondFragmentArgs by navArgs()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = args.anime
    }
}