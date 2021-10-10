package com.dteti.animapp.common.recyclerviewadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dteti.animapp.R
import com.dteti.animapp.databinding.AnimeListItemBinding
import com.dteti.animapp.services.animeservice.Anime

class AnimeAdapter(private val animes: LiveData<List<Anime>>): RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {
    inner class AnimeViewHolder(private val binding: AnimeListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Anime) = with(binding) {
            anime = item
        }
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        if (animes.value != null) {
            holder.bind(animes.value!![position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<AnimeListItemBinding>(
            inflater,
            R.layout.anime_list_item,
            parent,
            false
        )
        return AnimeViewHolder(binding)
    }

    override fun getItemCount(): Int = animes.value?.count() ?: 5
}