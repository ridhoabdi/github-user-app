package com.dicoding.submissiongithubapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissiongithubapp.data.remote.response.User
import com.dicoding.submissiongithubapp.databinding.ItemRowUserBinding

class ListUserAdapter() : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    private val listUser = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: java.util.ArrayList<User>) {
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .into(ivItemAvatar)
                tvItemUsername.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserAdapter.ViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ListUserAdapter.ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}