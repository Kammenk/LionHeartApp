package com.example.lionheartapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lionheartapp.models.Photos
import com.example.lionheartapp.models.PhotosItem
import com.example.lionheartapp.repository.Repository
import com.example.lionheartapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class MainViewModel @ViewModelInject constructor (
    private val repository: Repository,
    application: Application): AndroidViewModel(application) {

        var photosResponse: MutableLiveData<Resource<ArrayList<PhotosItem>>> = MutableLiveData()

        fun getPhotos(page: Int, limit: Int, clientID: String) = viewModelScope.launch {
            println("CALLED")
            getPhotosSafeCall(page,limit,clientID)
            println("response ${photosResponse.value}")
        }

    private suspend fun getPhotosSafeCall(page: Int, limit: Int, clientID: String) {
        if(hasInternetConnection()){
            return try{
                val response = repository.remote.getPhotos(page,limit,clientID)
                photosResponse.value = handlePhotosResponse(response)
            } catch (e: Exception){
                println(e.message)
                photosResponse.value = Resource.Error("No Photos Found")
            }
        } else {
            photosResponse.value = Resource.Error("No Internet Connection.")
        }
    }

    private fun handlePhotosResponse(response: Response<ArrayList<PhotosItem>>): Resource<ArrayList<PhotosItem>>? {
        when {
            response.message().toString().contains("timeout") -> {
                return Resource.Error("Timeout")
            }
            response.code() == 402 ->{
                return Resource.Error("API Key Limited")
            }
            response.body().isNullOrEmpty() -> {
                return Resource.Error("Photos not found")
            }
            response.isSuccessful -> {
                val photos = response.body()
                return  Resource.Success(photos!!)
            }
            else -> {
                println("in else")
                return Resource.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean{
            val connectivityManager = getApplication<Application>().getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
}