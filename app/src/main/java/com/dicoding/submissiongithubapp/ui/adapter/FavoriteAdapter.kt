package com.dicoding.submissiongithubapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissiongithubapp.data.local.entity.FavoriteEntity
import com.dicoding.submissiongithubapp.databinding.ItemRowUserBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val listFavorite = ArrayList<FavoriteEntity>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setListFavorites(favorite: java.util.ArrayList<FavoriteEntity>) {
        listFavorite.clear()
        listFavorite.addAll(favorite)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEntity: FavoriteEntity) {
            with(binding) {
                Glide.with(itemView)
                    .load(favoriteEntity.avatarUrl)
                    .into(ivItemAvatar)
                tvItemUsername.text = favoriteEntity.login
            }

            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(favoriteEntity)
            }
        }
    }

    override fun getItemCount(): Int = listFavorite.size

    interface OnItemClickCallback {
        fun onItemClicked(fav: FavoriteEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }


}