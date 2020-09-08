package com.pavelrukin.imagesearch.ui

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.pavelrukin.imagesearch.R
import com.pavelrukin.imagesearch.utils.Resource
import com.pavelrukin.imagesearch.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class ImageActivity : AppCompatActivity() {
    val TAG = "ImageActivity"
    val viewModel: ImageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextZone()
        fetchList()



    }


    fun editTextZone() {



        et_search.apply {



                setOnEditorActionListener { _, actionId, _ ->
                    val textInEditText = text.toString()
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        viewModel.getSearchImage(textInEditText)
                        hideKeyboard()
                        Log.d(TAG, "editTextZone: action true ")

                        Toast.makeText(context, textInEditText, android.widget.Toast.LENGTH_SHORT)
                            .show()
                    }
                    true

                }



        }


    }



    fun fetchList() {
        viewModel.images.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {

                    response.data?.let { response ->
                        val imageUrl = response.data[(0..response.data.size).random()].images.downsizedMedium.url
                        Log.d(TAG, "image url ${response.data.size}")

                        Glide.with(this)
                            .asGif()
                            .load(imageUrl)
                            .into(iv_image)

                    }
                }
                is Resource.Error -> {

                    response.message?.let { message ->

                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {

                }
            }
        })
    }


}
