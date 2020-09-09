package com.pavelrukin.imagesearch.db

import android.content.Context
import android.util.Log
import com.pavelrukin.imagesearch.model.ImageModel
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class RepoDao(val realm: Realm) {
    fun insertRepos(items: MutableList<ImageModel>){
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(items)
        realm.commitTransaction()
    }

}

