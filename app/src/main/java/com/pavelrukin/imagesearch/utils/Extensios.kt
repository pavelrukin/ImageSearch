package com.pavelrukin.imagesearch.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


fun EditText.hideKeyboard(): Boolean {
    return (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, 0)
}

