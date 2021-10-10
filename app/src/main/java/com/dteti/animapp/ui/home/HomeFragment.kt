package com.dteti.animapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dteti.animapp.common.recyclerviewadapter.AnimeAdapter
import com.dteti.animapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding.apply {
            this?.lifecycleOwner = this@HomeFragment
            this?.viewModel = homeViewModel

            val animeAdapter = AnimeAdapter(homeViewModel.animes, viewLifecycleOwner)

            this?.rvAnimeList.apply {
                this?.layoutManager = LinearLayoutManager(context)
                this?.adapter = animeAdapter
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}