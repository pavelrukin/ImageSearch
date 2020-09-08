package com.pavelrukin.imagesearch.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar_url")
    val avatarUrl: String, // https://media0.giphy.com/avatars/justin/Ym9RjQrTsFKT.gif
    @SerializedName("banner_image")
    val bannerImage: String, // https://media0.giphy.com/channel_assets/justino/c3v3Q6fKnSeR.gif
    @SerializedName("banner_url")
    val bannerUrl: String, // https://media0.giphy.com/channel_assets/justino/c3v3Q6fKnSeR.gif
    @SerializedName("display_name")
    val displayName: String, // Justin
    @SerializedName("is_verified")
    val isVerified: Boolean, // false
    @SerializedName("profile_url")
    val profileUrl: String, // https://giphy.com/justin/
    @SerializedName("username")
    val username: String // justin
)
