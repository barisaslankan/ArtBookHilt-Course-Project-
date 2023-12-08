package com.barisaslankan.artbookhilt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.barisaslankan.artbookhilt.databinding.ArtRowItemBinding
import com.barisaslankan.artbookhilt.model.Art
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtRecyclerAdapter @Inject() constructor(
    private val glide : RequestManager
) : Adapter<ArtRecyclerAdapter.ArtViewHolder>() {

    class ArtViewHolder(val binding: ArtRowItemBinding) : ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer  = AsyncListDiffer(this, diffUtil)

    var arts : List<Art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val binding = ArtRowItemBinding.inflate(LayoutInflater.from(parent.context))
        return ArtViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {

        val art = arts[position]

        glide.load(art.imageUrl).into(holder.binding.artRowImage)
        holder.binding.artRowTextArtName.text = "Name: ${art.name}"
        holder.binding.artRowTextArtistName.text = "Artist: ${art.artist}"
        holder.binding.artRowTextYear.text = "Year: ${art.year}"

    }

}