package com.example.lionheartapp.api

import com.example.lionheartapp.models.PhotoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.util.ArrayList

class RemoteAccess : PhotosAPI {

    override suspend fun getCategories(
        url: String,
        slug: String,
        page: Int,
        limit: Int,
        clientID: String
    ): ArrayList<PhotoItem> {
        val completeURL = "$url/$slug/photos?page=$page&per_page=$limit&client_id=$clientID"
        val resultURL = withContext(Dispatchers.IO) {
            (URL(completeURL).readText())
        }
        val jsonArray = JSONArray(resultURL)
        var counter = 0
        var jsonObject: JSONObject
        val arrayList = ArrayList<PhotoItem>(emptyList())
        while (counter < limit) {
            jsonObject = jsonArray.getJSONObject(counter)
            arrayList.add(
                PhotoItem(
                    photoCreatorName = jsonObject.getJSONObject("user").getString("name"),
                    photoCreatorImage = jsonObject.getJSONObject("user")
                        .getJSONObject("profile_image").getString("small"),
                    photoDescription = jsonObject.getString("alt_description"),
                    photoLikes = jsonObject.getString("likes").toInt(),
                    photoUrlRegular = jsonObject.getJSONObject("urls").getString("regular"),
                    photoUrlSmall = jsonObject.getJSONObject("urls").getString("small")
                )
            )
            counter++
        }
        return arrayList
    }

    override suspend fun getPhotos(
        url: String,
        page: Int,
        limit: Int,
        clientID: String
    ): ArrayList<PhotoItem> {
        val completeURL = "$url?page=$page&per_page=$limit&client_id=$clientID"
        val resultURL = withContext(Dispatchers.IO) {
            (URL(completeURL).readText())
        }
        val jsonArray = JSONArray(resultURL)
        var counter = 0
        var jsonObject: JSONObject
        val arrayList = ArrayList<PhotoItem>(emptyList())
        while (counter < limit) {
            jsonObject = jsonArray.getJSONObject(counter)
            arrayList.add(
                PhotoItem(
                    photoCreatorName = jsonObject.getJSONObject("user").getString("name"),
                    photoCreatorImage = jsonObject.getJSONObject("user")
                        .getJSONObject("profile_image").getString("small"),
                    photoDescription = jsonObject.getString("alt_description"),
                    photoLikes = jsonObject.getString("likes").toInt(),
                    photoUrlRegular = jsonObject.getJSONObject("urls").getString("regular"),
                    photoUrlSmall = jsonObject.getJSONObject("urls").getString("small")
                )
            )
            counter++
        }
        return arrayList
    }
}