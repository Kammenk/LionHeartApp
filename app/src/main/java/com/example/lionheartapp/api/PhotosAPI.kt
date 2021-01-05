package com.example.lionheartapp.api

import com.example.lionheartapp.models.PhotoItem
import java.util.ArrayList

interface PhotosAPI {

    suspend fun getPhotos(
        url: String,
        page: Int,
        limit: Int,
        clientID: String
    ): ArrayList<PhotoItem>

}