package com.chs.youranimelist.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chs.youranimelist.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchViewModel
    private var _binding: ActivitySearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}