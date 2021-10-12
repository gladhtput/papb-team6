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
import com.dteti.animapp.services.animeservice.Anime

class AnimeAdapter(
    private val animes: LiveData<List<Anime>>,
    lifecycleOwner: LifecycleOwner,
    private val onClickListener: OnItemClickListener
    ) : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {
    init {
        animes.observe(lifecycleOwner, {
            notifyDataSetChanged()
        })
    }

    interface OnItemClickListener {
        fun onItemClick(anime: Anime)
    }

    inner class AnimeViewHolder(private val binding: AnimeListItemBinding, private val listener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Anime) = with(binding) {
            anime = item

            itemView.setOnClickListener(View.OnClickListener {
                listener.onItemClick(item)
            })
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
        return AnimeViewHolder(binding, onClickListener)
    }

    override fun getItemCount(): Int = animes.value?.count() ?: 5
}