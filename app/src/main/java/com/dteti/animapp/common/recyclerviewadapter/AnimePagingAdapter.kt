package com.dteti.animapp.common.recyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dteti.animapp.databinding.AnimeListItemBinding
import com.dteti.animapp.dto.AnimeSummary

class AnimePagingAdapter(
    diffCallback: DiffUtil.ItemCallback<AnimeSummary>,
    private val onClickListener: (anime: AnimeSummary?) -> Unit
) : PagingDataAdapter<AnimeSummary, AnimePagingAdapter.AnimeViewHolder>(diffCallback) {

    inner class AnimeViewHolder(
        private val binding: AnimeListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: AnimeSummary?) {
            binding.anime = anime

            binding.cardviewAnimeListItem.setOnClickListener {
                onClickListener(anime)
            }
        }
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = AnimeListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return AnimeViewHolder(binding)
    }
}