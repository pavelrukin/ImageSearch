package com.pavelrukin.imagesearch.api

import com.pavelrukin.imagesearch.model.ImageSearchResponse
import com.pavelrukin.imagesearch.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageSearchApi {


    @GET("v1/stickers/search")
    suspend fun searchImage(
        @Query("q")
        name: String = "Cat",
        @Query("api_key")
        apiKey: String = API_KEY,

    ): Response<ImageSearchResponse>

}
