package com.pavelrukin.imagesearch.ui

import com.pavelrukin.imagesearch.api.RetrofitInstance

class ImageRepository {
    suspend fun searchImage(searchQuery:String) = RetrofitInstance.api.searchImage(searchQuery)
}
