package com.example.lionheartapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lionheartapp.api.RemoteAccess
import com.example.lionheartapp.models.PhotoItem
import com.example.lionheartapp.repository.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var photosResponse: MutableLiveData<ArrayList<PhotoItem>> = MutableLiveData()

    fun getPhotos(url: String, page: Int, limit: Int, clientID: String) = viewModelScope.launch {
        getPhotosSafeCall(url, page, limit, clientID)
    }

    private suspend fun getPhotosSafeCall(url: String, page: Int, limit: Int, clientID: String) {
        if (hasInternetConnection()) {
            return try {
                val remoteAccess = RemoteAccess()
                val repository = Repository(remoteAccess)
                val response = repository.remoteAccess.getPhotos(url, page, limit, clientID)
                photosResponse.value = response
                println("printing from viewmodel = $response")
            } catch (e: Exception) {
                println("in catch")
                println(e.printStackTrace())
            }
        }
    }

//    private fun handlePhotosResponse(response: ArrayList<PhotoItem>): Resource<ArrayList<PhotosItem>>? {
//        when {
//            response
//            response.message().toString().contains("timeout") -> {
//                return Resource.Error("Timeout")
//            }
//            response.code() == 402 ->{
//                return Resource.Error("API Key Limited")
//            }
//            response.body().isNullOrEmpty() -> {
//                return Resource.Error("Photos not found")
//            }
//            response.isSuccessful -> {
//                val photos = response.body()
//                return  Resource.Success(photos!!)
//            }
//            else -> {
//                return Resource.Error(response.message())
//            }
//        }
//    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}