package com.dteti.animapp.common.recyclerviewadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.dteti.animapp.common.enums.LoadItemState
import com.dteti.animapp.databinding.AnimeLoadItemBinding

class AnimeLoadStateAdapter(
    private val retryListener: () -> Unit
) : LoadStateAdapter<AnimeLoadStateAdapter.AnimeLoadStateViewHolder>() {
    inner class AnimeLoadStateViewHolder(
        private val binding: AnimeLoadItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryListener = retryListener
        }

        fun bind(loadState: LoadState) {
            when {
                loadState is LoadState.Error -> {
                    binding.state = LoadItemState.ERROR
                }
                loadState is LoadState.Loading -> {
                    binding.state = LoadItemState.LOADING
                }
                loadState is LoadState.NotLoading && loadState.endOfPaginationReached -> {
                    binding.state = LoadItemState.FINISHED
                }
                else -> {
                    binding.state = LoadItemState.NONE
                }
            }
        }
    }

    override fun onBindViewHolder(holder: AnimeLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): AnimeLoadStateViewHolder {
        val binding = AnimeLoadItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return AnimeLoadStateViewHolder(binding)
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Loading || loadState is LoadState.Error || (loadState is LoadState.NotLoading && loadState.endOfPaginationReached)
    }
}