package com.assignment.myapplication1

data class ImageModel(
    var images: List<Image>
)


data class Image(
    var tittle: String,
    var description: String,
    var imageURL: String
)