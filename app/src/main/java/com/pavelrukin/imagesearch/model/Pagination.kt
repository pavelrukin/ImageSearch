package com.pavelrukin.imagesearch.model


import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("count")
    val count: Int, // 50
    @SerializedName("offset")
    val offset: Int, // 0
    @SerializedName("total_count")
    val totalCount: Int // 17392
)
