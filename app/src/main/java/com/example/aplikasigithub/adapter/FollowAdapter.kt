package com.example.aplikasigithub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.aplikasigithub.databinding.UserItemBinding
import com.example.aplikasigithub.models.follow.ResponseFollow

class FollowAdapter: RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {

    private val list = ArrayList<ResponseFollow>()
    fun setList(user: ArrayList<ResponseFollow>){
        list.clear()
        list.addAll(user)
        notifyDataSetChanged()

    }
    inner class FollowViewHolder(val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ResponseFollow){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val v = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(v)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}