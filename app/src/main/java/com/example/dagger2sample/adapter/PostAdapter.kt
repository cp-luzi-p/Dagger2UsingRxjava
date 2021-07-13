package com.example.dagger2sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.dagger2sample.databinding.ItemPostBinding
import com.example.dagger2sample.model.Posts

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private lateinit var binding: ItemPostBinding

    var albums = emptyList<Posts>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val albumList = albums[position]
        holder.bind(albumList)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(posts: Posts) {
            binding.postId.text = posts.postId.toString()
            binding.postTitle.text = posts.postTitle
            binding.postBody.text = posts.postBody
        }
    }
}