package com.pavelrukin.imagesearch.model


import com.google.gson.annotations.SerializedName

data class DownsizedMedium(
    @SerializedName("height")
    val height: String, // 386
    @SerializedName("size")
    val size: String, // 48996
    @SerializedName("url")
    val url: String, // https://media0.giphy.com/media/4BJCvMoLPePq8/giphy.gif?cid=d3d65f987rcisddky9tbgu3h8daje7pfojryof2i16n9yejq&rid=giphy.gif
    @SerializedName("width")
    val width: String // 269
)
