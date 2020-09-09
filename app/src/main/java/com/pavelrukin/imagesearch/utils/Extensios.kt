package com.pavelrukin.imagesearch.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.pavelrukin.imagesearch.db.RepoDao
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults


fun EditText.hideKeyboard(): Boolean {
    return (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, 0)
}
fun Realm.repoDao(): RepoDao = RepoDao(this)

fun <T: RealmModel> RealmResults<T>.asLiveData() = LiveRealmData(this)
