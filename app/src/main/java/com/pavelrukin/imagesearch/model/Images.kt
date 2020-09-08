package com.pavelrukin.imagesearch.model


import com.google.gson.annotations.SerializedName

data class Images(

    @SerializedName("downsized_medium")
    val downsizedMedium: DownsizedMedium,


)
