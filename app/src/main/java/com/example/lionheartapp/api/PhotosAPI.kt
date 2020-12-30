package com.example.lionheartapp.api

import com.example.lionheartapp.models.Photos
import com.example.lionheartapp.models.PhotosItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosAPI {

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") limit: Int,
        @Query("client_id") clientID: String
    ): Response<ArrayList<PhotosItem>>
}