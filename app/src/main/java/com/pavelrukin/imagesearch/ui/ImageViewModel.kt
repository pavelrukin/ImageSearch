package com.pavelrukin.imagesearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelrukin.imagesearch.model.ImageModel
import com.pavelrukin.imagesearch.model.ImageSearchResponse
import com.pavelrukin.imagesearch.utils.LiveRealmData
import com.pavelrukin.imagesearch.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class ImageViewModel(private val imageRepository: ImageRepository, private val realmRepository: RealmRepository) : ViewModel() {


    val images: MutableLiveData<Resource<ImageSearchResponse>> = MutableLiveData()
val savedValue : MutableLiveData<List<ImageModel>> = MutableLiveData()

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


fun getAllData(   ){
      realmRepository.getAllData()


}

    fun saveData(url: String,searchText:String){
        realmRepository.saveData(url, searchText)

    }
    fun deleteData(imageModel: ImageModel){
        realmRepository.deleteData(imageModel)
    }

    override fun onCleared() {
        realmRepository.realm.close()
        super.onCleared()
    }
}
