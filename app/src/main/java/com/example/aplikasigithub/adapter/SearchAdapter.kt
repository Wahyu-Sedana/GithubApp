package com.example.aplikasigithub.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplikasigithub.databinding.UserItemBinding
import com.example.aplikasigithub.helper.FavoriteUser
import com.example.aplikasigithub.models.searchdata.ResponseSearchData

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val list = ArrayList<ResponseSearchData>()
    private var onItemClick: OnItemClickCallBack? = null

    fun setOnItemCallBack(onItemClick: OnItemClickCallBack) {
        this.onItemClick = onItemClick
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(user: List<ResponseSearchData>) {
        list.clear()
        list.addAll(user)
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ResponseSearchData) {
            binding.root.setOnClickListener {
                onItemClick?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .centerCrop()
                    .into(userAvatar)
                username.text = user.login
                url.text = user.url
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val v = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(v)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallBack {
        fun onItemClicked(data: ResponseSearchData)
    }
}