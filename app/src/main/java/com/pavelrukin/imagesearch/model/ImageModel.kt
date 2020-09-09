package com.pavelrukin.imagesearch.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class ImageModel(
    var searchText: String? = null,
    var url: String? = null
) : RealmObject()

