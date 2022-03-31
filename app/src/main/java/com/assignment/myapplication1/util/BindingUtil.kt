package com.assignment.myapplication1.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.assignment.myapplication1.Image
import com.assignment.myapplication1.R
import com.bumptech.glide.Glide

object BindingUtil {
    @BindingAdapter("loadImage")
    fun AppCompatImageView.setDrawableImage(image: Image) {
        Glide.with(this).load(image.imageURL).into(findViewById(R.id.imageView))
    }
}