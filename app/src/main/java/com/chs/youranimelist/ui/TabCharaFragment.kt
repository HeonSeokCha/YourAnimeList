package com.chs.youranimelist.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.R
import com.chs.youranimelist.SpacesItemDecoration
import com.chs.youranimelist.adapter.CharaAdapter
import com.chs.youranimelist.databinding.FragmentTabCharaBinding

class TabCharaFragment : Fragment() {
    private lateinit var binding: FragmentTabCharaBinding
    private lateinit var charaAdapter: CharaAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabCharaBinding.inflate(inflater,container,false)
        charaAdapter = CharaAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("chara") }?.apply {
            charaAdapter.submitList((getSerializable("chara") as List<AnimeDetailQuery.Node>))
            Log.d("chara",(getSerializable("chara") as List<AnimeDetailQuery.Node>).toString())
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvAnimeChara.apply {
            this.adapter = charaAdapter
            this.layoutManager = GridLayoutManager(this@TabCharaFragment.context,2)
        }
    }
}