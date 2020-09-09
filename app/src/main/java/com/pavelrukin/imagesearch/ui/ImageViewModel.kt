package com.pavelrukin.imagesearch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelrukin.imagesearch.model.ImageSearchResponse
import com.pavelrukin.imagesearch.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class ImageViewModel(private val imageRepository: ImageRepository) : ViewModel() {


    val images: MutableLiveData<Resource<ImageSearchResponse>> = MutableLiveData()


    fun getSearchImage(searchQuery: String) = viewModelScope.launch {
        images.postValue(Resource.Loading())
        val response = imageRepository.searchImage(searchQuery)
        images.postValue(handledCurrentWeather(response))
    }

    private fun handledCurrentWeather(response: Response<ImageSearchResponse>): Resource<ImageSearchResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}
