package com.example.lionheartapp.api

import com.example.lionheartapp.models.PhotoItem
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.util.ArrayList

class RemoteAccess : PhotosAPI {

    override suspend fun getPhotos(
        url: String,
        page: Int,
        limit: Int,
        clientID: String
    ): ArrayList<PhotoItem> {
        val completeURL = "$url?page=$page&per_page=$limit&client_id=$clientID"
        println("this is the complete url $completeURL")
        val resultURL = URL(completeURL).readText()
        val jsonArray = JSONArray(resultURL)
        var counter = 0
        var jsonObject: JSONObject
        var arrayList = ArrayList<PhotoItem>(emptyList())
        while (counter < limit) {
            jsonObject = jsonArray.getJSONObject(counter)
            arrayList.add(
                PhotoItem(
                    photoCreatorName = jsonObject.getJSONObject("user").getString("name"),
                    photoCreatorImage = jsonObject.getJSONObject("user")
                        .getJSONObject("profile_image").getString("small"),
                    photoDescription = jsonObject.getString("alt_description"),
                    photoLikes = jsonObject.getString("likes").toInt(),
                    photoUrl = jsonObject.getJSONObject("urls").getString("regular")
                )
            )

            counter++
        }
        return arrayList
    }
}