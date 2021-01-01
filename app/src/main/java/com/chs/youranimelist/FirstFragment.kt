package com.chs.youranimelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chs.youranimelist.databinding.FragmentFirstBinding
import com.chs.youranimelist.network.api.AnimeService
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.network.services.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {
    private var binding: FragmentFirstBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding!!.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun getPagerAnimeList() {
        GlobalScope.launch(Dispatchers.IO) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}