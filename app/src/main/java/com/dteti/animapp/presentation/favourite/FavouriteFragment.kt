package com.dteti.animapp.presentation.favourite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dteti.animapp.DetailActivity
import com.dteti.animapp.common.recyclerviewadapter.FavoriteAdapter
import com.dteti.animapp.databinding.FragmentFavouriteBinding
import com.dteti.animapp.dto.AnimeSummary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private var _binding: FragmentFavouriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favouriteViewModel

        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding.apply {
            this?.viewModel = favouriteViewModel

            val favoriteAdapter = FavoriteAdapter(
                favouriteViewModel.animes,
                viewLifecycleOwner,
                object: FavoriteAdapter.OnItemClickListener {
                    override fun onItemClick(anime: AnimeSummary) {
                        openAnimeDetails(anime.id.toString())
                    }
                }
            )

            this?.favoriteList.apply {
                this?.layoutManager = GridLayoutManager(context,3,RecyclerView.VERTICAL,false)
                this?.adapter = favoriteAdapter
            }
        }


        return root
    }

    override fun onResume() {
        super.onResume()

        favouriteViewModel.loadFavoriteAnimes()
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