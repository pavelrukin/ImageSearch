package com.pavelrukin.imagesearch.model


import com.google.gson.annotations.SerializedName

data class Data(


    @SerializedName("bitly_gif_url")
    val bitlyGifUrl: String, // http://gph.is/1XfAbKg
    @SerializedName("bitly_url")
    val bitlyUrl: String, // http://gph.is/1XfAbKg
    @SerializedName("content_url")
    val contentUrl: String,
    @SerializedName("embed_url")
    val embedUrl: String, // https://giphy.com/embed/4BJCvMoLPePq8
    @SerializedName("featured_tags")
    val featuredTags: List<Any>,
    @SerializedName("id")
    val id: String, // 4BJCvMoLPePq8
    @SerializedName("images")
    val images: Images,
    @SerializedName("import_datetime")
    val importDatetime: String, // 2016-05-16 17:33:33
    @SerializedName("is_sticker")
    val isSticker: Int, // 1
    @SerializedName("rating")
    val rating: String, // g
    @SerializedName("slug")
    val slug: String, // 4BJCvMoLPePq8
    @SerializedName("source")
    val source: String, // http://www.keytarhq.com/animals-playing-guitar-funny-animated-gifs.html
    @SerializedName("source_post_url")
    val sourcePostUrl: String, // http://www.keytarhq.com/animals-playing-guitar-funny-animated-gifs.html
    @SerializedName("source_tld")
    val sourceTld: String, // www.keytarhq.com
    @SerializedName("tags")
    val tags: List<Any>,
    @SerializedName("title")
    val title: String, // Cat Rocking Sticker
    @SerializedName("trending_datetime")
    val trendingDatetime: String, // 2020-05-07 12:45:07
    @SerializedName("type")
    val type: String, // gif
    @SerializedName("url")
    val url: String, // https://giphy.com/stickers/4BJCvMoLPePq8
    @SerializedName("user")
    val user: User,
    @SerializedName("user_tags")
    val userTags: List<Any>,
    @SerializedName("username")
    val username: String
)
