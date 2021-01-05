package com.example.lionheartapp.data

import com.example.lionheartapp.api.RemoteAccess
import com.example.lionheartapp.models.PhotoItem

class RemoteDataSource(
    private val remoteAccess: RemoteAccess
) {

    suspend fun getPhotos(
        url: String,
        page: Int,
        limit: Int,
        clientID: String
    ): ArrayList<PhotoItem> {
        return remoteAccess.getPhotos(url, page, limit, clientID)
    }
}