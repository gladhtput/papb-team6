package com.dteti.animapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dteti.animapp.DetailActivity
import com.dteti.animapp.R
import com.dteti.animapp.common.recyclerviewadapter.AnimeAdapter
import com.dteti.animapp.databinding.FragmentHomeBinding
import com.dteti.animapp.dto.AnimeSummary
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding.apply {
            this?.viewModel = homeViewModel

            val animeAdapter = AnimeAdapter(
                homeViewModel.animes,
                viewLifecycleOwner,
                object: AnimeAdapter.OnItemClickListener {
                    override fun onItemClick(anime: AnimeSummary) {
                        openAnimeDetails(anime.id.toString())
                    }
                }
            )

            this?.rvAnimeList.apply {
                this?.layoutManager = LinearLayoutManager(context)
                this?.adapter = animeAdapter
            }
        }

        homeViewModel.mainOverlayVisible.observe(viewLifecycleOwner, {
            view?.findViewById<ConstraintLayout>(R.id.clHome)?.visibility = if (it) View.VISIBLE else View.GONE
        })
        homeViewModel.noConnectionOverlayVisible.observe(viewLifecycleOwner, {
            view?.findViewById<ConstraintLayout>(R.id.clNoConnection)?.visibility = if (it) View.VISIBLE else View.GONE
        })

        return root
    }

    override fun onStart() {
        super.onStart()

        view?.findViewById<Button>(R.id.btnHomeToFavorite)?.setOnClickListener {
            navigateToFavorite()
        }
    }

    private fun navigateToFavorite() {
        findNavController().navigate(R.id.home_to_favorite)
    }

    private fun openAnimeDetails(animeId: String) {
        val openDetailsIntent = Intent(context, DetailActivity::class.java).apply {
            putExtra("animeId", animeId)
        }
        startActivity(openDetailsIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}