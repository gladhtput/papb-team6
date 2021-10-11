package com.dteti.animapp.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dteti.animapp.common.recyclerviewadapter.FavoriteAdapter
import com.dteti.animapp.databinding.FragmentFavouriteBinding
import java.util.ArrayList

class FavouriteFragment : Fragment() {

    private lateinit var favouriteViewModel: FavouriteViewModel
    private var _binding: FragmentFavouriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favouriteViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)

        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding.apply {
            this?.lifecycleOwner = this@FavouriteFragment
            this?.viewModel = favouriteViewModel

            val favoriteAdapter = FavoriteAdapter(favouriteViewModel.animes,viewLifecycleOwner)

            this?.favoriteList.apply {
                this?.layoutManager = GridLayoutManager(context,3,RecyclerView.VERTICAL,false)
                this?.adapter = favoriteAdapter
            }
        }


        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}