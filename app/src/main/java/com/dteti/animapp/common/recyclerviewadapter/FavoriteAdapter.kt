package com.dteti.animapp.common.recyclerviewadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.dteti.animapp.R
import com.dteti.animapp.databinding.FavoriteItemBinding
import com.dteti.animapp.dto.AnimeSummary

class FavoriteAdapter(
    private val animes: LiveData<List<AnimeSummary>>,
    lifecycleOwner: LifecycleOwner,
    private val onClickListener: FavoriteAdapter.OnItemClickListener
    ) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    init {
        animes.observe(lifecycleOwner, {
            notifyDataSetChanged()
        })
    }

    interface OnItemClickListener {
        fun onItemClick(anime: AnimeSummary)
    }

    inner class FavoriteViewHolder(private val binding: FavoriteItemBinding, private val listener: FavoriteAdapter.OnItemClickListener): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AnimeSummary)= with(binding) {
            anime = item

            itemView.setOnClickListener(View.OnClickListener {
                listener.onItemClick(item)
            })
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
        return FavoriteViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        if (animes.value != null) {
            holder.bind(animes.value!![position])
        }
    }

    override fun getItemCount(): Int = animes.value?.count() ?: 5
}