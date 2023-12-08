package com.barisaslankan.artbookhilt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.barisaslankan.artbookhilt.databinding.ImageRowItemBinding
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    val glide : RequestManager
): Adapter<ImageRecyclerAdapter.ImageViewHolder>() {

    class ImageViewHolder (val binding : ImageRowItemBinding) : ViewHolder(binding.root)

    private var onItemClickListener : ((String) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var images : List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageRowItemBinding.inflate(LayoutInflater.from(parent.context))
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun setOnImageClickListener(listener : (String) -> Unit){
        onItemClickListener = listener
    }
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val url = images[position]
        glide.load(url).into(holder.binding.imageRowImage)
        holder.itemView.setOnClickListener{
            onItemClickListener?.let {
                it(url)
            }
        }
    }
}