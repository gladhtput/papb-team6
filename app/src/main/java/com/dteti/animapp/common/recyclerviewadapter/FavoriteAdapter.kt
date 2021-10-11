package com.dteti.animapp.common.recyclerviewadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dteti.animapp.R
import com.dteti.animapp.databinding.AnimeListItemBinding
import com.dteti.animapp.databinding.FavoriteItemBinding
import com.dteti.animapp.services.animeservice.Anime

class FavoriteAdapter(private val animes: LiveData<List<Anime>>, lifecycleOwner: LifecycleOwner): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    init {
        animes.observe(lifecycleOwner, {
            notifyDataSetChanged()
        })
    }

    inner class FavoriteViewHolder(private val binding: FavoriteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Anime)= with(binding) {
            anime = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<FavoriteItemBinding>(
            inflater,
            R.layout.favorite_item,
            parent,
            false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        if (animes.value != null) {
            holder.bind(animes.value!![position])
        }
    }

    override fun getItemCount(): Int = animes.value?.count() ?: 5
}