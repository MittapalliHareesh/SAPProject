package com.assignment.myapplication1.api

import com.assignment.myapplication1.ImageModel
import retrofit2.http.GET

interface ApiService {

    @GET("99066355-8f5e-4c9d-b400-d5bdf26911b6")
    suspend fun getImages(): ImageModel
}