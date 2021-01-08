package com.example.lionheartapp.api

import com.example.lionheartapp.models.PhotoItem
import java.util.ArrayList

interface PhotosAPI {

    //Used to get specific categories
    suspend fun getCategories(
        url: String,
        slug: String,
        page: Int,
        limit: Int,
        clientID: String
    ): ArrayList<PhotoItem>

    //Used to get a number of photos from a specific page
    suspend fun getPhotos(
        url: String,
        page: Int,
        limit: Int,
        clientID: String
    ): ArrayList<PhotoItem>

}