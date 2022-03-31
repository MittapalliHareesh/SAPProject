package com.assignment.myapplication1.imageRepository

import com.assignment.myapplication1.api.ApiService
import javax.inject.Inject

class ImageRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getImages() = apiService.getImages()
}