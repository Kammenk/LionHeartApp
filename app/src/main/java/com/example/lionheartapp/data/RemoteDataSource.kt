package com.example.lionheartapp.data

import com.example.lionheartapp.api.PhotosAPI
import com.example.lionheartapp.models.Photos
import com.example.lionheartapp.models.PhotosItem
import retrofit2.Response
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val photosAPI: PhotosAPI
) {

    suspend fun getPhotos(page: Int, limit: Int, clientID: String): Response<ArrayList<PhotosItem>> {
        println("in remote")
        return photosAPI.getPhotos(page,limit,clientID)
    }
}