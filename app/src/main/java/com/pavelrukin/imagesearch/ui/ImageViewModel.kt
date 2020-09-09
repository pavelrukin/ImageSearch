package com.pavelrukin.imagesearch.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pavelrukin.imagesearch.ImageSearchApp
import com.pavelrukin.imagesearch.model.ImageModel
import com.pavelrukin.imagesearch.model.ImageSearchResponse
import com.pavelrukin.imagesearch.repository.ImageRepository
import com.pavelrukin.imagesearch.repository.RealmRepository
import com.pavelrukin.imagesearch.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class ImageViewModel(
    private val imageRepository: ImageRepository,
    private val realmRepository: RealmRepository,
    app: ImageSearchApp
) : AndroidViewModel(app) {


    val images: MutableLiveData<Resource<ImageSearchResponse>> = MutableLiveData()
    var imagesResponse: ImageSearchResponse? = null


    fun getSearchImage(searchQuery: String) = viewModelScope.launch {
        safeImage(searchQuery)
    }

    private fun handleImage(response: Response<ImageSearchResponse>): Resource<ImageSearchResponse> {
        if (response.isSuccessful && response.body() != null) {

            response.body()?.let { resultResponse ->
           if (resultResponse.data[0] == resultResponse.data[0]) {

                }  else {
                    val oldMovies = imagesResponse?.data
                    val newMovies = resultResponse.data
                    oldMovies?.addAll(0,newMovies)
                }
                Log.d(TAG, "handleImage: $resultResponse")
                return Resource.Success(imagesResponse ?: resultResponse)
            }
        }

        return Resource.Error(response.message())
    }

    private suspend fun safeImage(searchQuery: String) {
        images.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                images.postValue(Resource.Loading())
                val response = imageRepository.searchImage(searchQuery)
                images.postValue(handleImage(response))
            } else {
                images.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {

                is IOException -> images.postValue(Resource.Error("Network Failure"))
                else -> images.postValue(Resource.Error("Image not found"))
            }
        }


    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<ImageSearchApp>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    fun getAllData() {
        realmRepository.getAllData()
    }

    fun saveData(url: String, searchText: String) {
        realmRepository.saveData(url, searchText)
    }

    fun deleteData(imageModel: ImageModel) {
        realmRepository.deleteData(imageModel)
    }

    override fun onCleared() {
        realmRepository.realm.close()
        super.onCleared()
    }
}
