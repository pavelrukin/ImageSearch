package com.pavelrukin.imagesearch.model


import com.google.gson.annotations.SerializedName

data class ImageSearchResponse(
    @SerializedName("data")
    val data: MutableList<Data>,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("pagination")
    val pagination: Pagination
)
