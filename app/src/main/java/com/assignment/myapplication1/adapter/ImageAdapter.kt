package com.assignment.myapplication1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.myapplication1.Image
import com.assignment.myapplication1.databinding.ImagedataBinding

class ImageAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Image, ImageAdapter.CustomViewHolder>(ImageDataDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val imageItemBinding = ImagedataBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CustomViewHolder(imageItemBinding)
    }

    override fun onBindViewHolder(holderCustom: CustomViewHolder, position: Int) {
        holderCustom.bind(getItem(position), listener)
    }

    class CustomViewHolder(private val binding: ImagedataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageItem: Image, listener: OnItemClickListener) {
            binding.clickListener = listener
            binding.imageData = imageItem
            binding.executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onImageClick(image: Image)
    }
}

class ImageDataDiffCallback : DiffUtil.ItemCallback<Image>() {
    /**
     * This method will be called to check whether old and new items are the same.
     * @param oldItem Indicates model class
     * @param newItem Indicates model class
     */
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.imageURL == newItem.imageURL
    }

    /**
     * This method will be called to check whether old and new items represent the same item visually.
     * This will only be called when areItemsTheSame() returns true.
     * @param oldItem Indicates model class
     * @param newItem Indicates model class
     */
    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }
}