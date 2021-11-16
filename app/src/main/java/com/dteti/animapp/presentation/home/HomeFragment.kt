package com.dteti.animapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dteti.animapp.DetailActivity
import com.dteti.animapp.R
import com.dteti.animapp.common.recyclerviewadapter.AnimePagingAdapter
import com.dteti.animapp.common.recyclerviewadapter.AnimeLoadStateAdapter
import com.dteti.animapp.databinding.FragmentHomeBinding
import com.dteti.animapp.dto.AnimeSummary
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val pagingAdapter = AnimePagingAdapter(
        object : DiffUtil.ItemCallback<AnimeSummary>() {
            override fun areItemsTheSame(
                oldItem: AnimeSummary,
                newItem: AnimeSummary
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: AnimeSummary,
                newItem: AnimeSummary
            ) = oldItem == newItem

        }) {
        openAnimeDetails(it?.id.toString())
    }

    private val loadAdapter = AnimeLoadStateAdapter {
        pagingAdapter.retry()
    }

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

            this?.rvAnimeList.apply {
                this?.layoutManager = LinearLayoutManager(context)
                this?.adapter = ConcatAdapter(pagingAdapter, loadAdapter)
            }
        }

        lifecycleScope.launch {
            launch {
                homeViewModel.flow.collectLatest {
                    pagingAdapter.submitData(it)
                }
            }

            launch {
                pagingAdapter.loadStateFlow.collectLatest {
                    loadAdapter.loadState = it.append
                }
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

        homeViewModel.isLoading.observe(viewLifecycleOwner, {
            view?.findViewById<SwipeRefreshLayout>(R.id.swr_HomeRefresh)?.isRefreshing = it
        })
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