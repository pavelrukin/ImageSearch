package com.pavelrukin.imagesearch.model


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("msg")
    val msg: String, // OK
    @SerializedName("response_id")
    val responseId: String, // 7rcisddky9tbgu3h8daje7pfojryof2i16n9yejq
    @SerializedName("status")
    val status: Int // 200
)
