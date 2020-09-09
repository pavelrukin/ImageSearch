package com.pavelrukin.imagesearch.ui

import android.util.Log
import androidx.lifecycle.LiveData
import com.pavelrukin.imagesearch.db.RepoDao
import com.pavelrukin.imagesearch.model.ImageModel
import com.pavelrukin.imagesearch.utils.LiveRealmData
import com.pavelrukin.imagesearch.utils.repoDao
import io.realm.Realm
import io.realm.RealmResults
import io.realm.exceptions.RealmException

class RealmRepository  {

    val TAG = "REALM_REPOSITORY"
val realm:Realm = Realm.getDefaultInstance()

    fun deleteData(imageModel: ImageModel){

        realm.beginTransaction()
        try {
            realm.where(ImageModel::class.java).equalTo("url", imageModel.url).findFirst().let {
                it?.deleteFromRealm()
            }
        } catch (e: RealmException) {
            Log.d(TAG, "onDeleted Item: ${e.message}")
        }
        realm.commitTransaction()
    }

    fun saveData(url: String,searchText:String){
        realm.beginTransaction()
        try {
            val result: ImageModel? = realm.createObject(ImageModel::class.java)
            result?.searchText = searchText
            result?.url = url
            Log.d(TAG, "saveData OK: ${searchText} ==== ${url}")
        } catch (e: RealmException) {
            Log.d(TAG, "saveData: ${e.message}")
        }
        realm.commitTransaction()

    }
    fun getAllData( ) = realm.where(ImageModel::class.java).findAll().let { it }

}
