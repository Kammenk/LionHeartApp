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

    //Used to store the data from the api
    var photosResponse: MutableLiveData<ArrayList<PhotoItem>> = MutableLiveData()

    fun getPhotos(url: String, page: Int, limit: Int, clientID: String) = viewModelScope.launch {
        getPhotosSafeCall(url, page, limit, clientID)
    }

    fun getCategories(url: String, slug: String, page: Int, limit: Int, clientID: String) =
        viewModelScope.launch {
            getCategoriesSafeCall(url, slug, page, limit, clientID)
        }

    //Used to check if there is internet connectivity before making a call
    private suspend fun getCategoriesSafeCall(
        url: String,
        slug: String,
        page: Int,
        limit: Int,
        clientID: String
    ) {
        if (hasInternetConnection()) {
            return try {
                val remoteAccess = RemoteAccess()
                val repository = Repository(remoteAccess)
                val response =
                    repository.remoteAccess.getCategories(url, slug, page, limit, clientID)
                photosResponse.value = response
            } catch (e: Exception) {
                println(e.printStackTrace())
            }
        }
    }

    //Used to check if there is internet connectivity before making a call
    private suspend fun getPhotosSafeCall(url: String, page: Int, limit: Int, clientID: String) {
        if (hasInternetConnection()) {
            return try {
                val remoteAccess = RemoteAccess()
                val repository = Repository(remoteAccess)
                val response = repository.remoteAccess.getPhotos(url, page, limit, clientID)
                photosResponse.value = response
            } catch (e: Exception) {
                println(e.printStackTrace())
            }
        }
    }

    //Used to check if there is any type of internet connectivity(wifi, mobile data etc.)
    fun hasInternetConnection(): Boolean {
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