package com.pavelrukin.imagesearch.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pavelrukin.imagesearch.R
import com.pavelrukin.imagesearch.model.ImageModel
import com.pavelrukin.imagesearch.utils.Resource
import com.pavelrukin.imagesearch.utils.hideKeyboard
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.exceptions.RealmException
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ImageActivity : AppCompatActivity() {
    val TAG = "ImageActivity"
    val viewModel: ImageViewModel by viewModel()
    lateinit var realm: Realm
    lateinit var dataAdapter: DataAdapter

    lateinit var realmListener: RealmChangeListener<Realm>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        realm = Realm.getDefaultInstance()

        initView()


        editTextZone()
        fetchList()


    }


/*
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }*/
    fun initView(){
        setupRecyclerView()
        clickDelete()
        realmListener = RealmChangeListener {
            setupRecyclerView()
            clickDelete()
        }
        realm.addChangeListener(realmListener)
    }
    private fun setupRecyclerView() {
        dataAdapter = DataAdapter()
        recyclerView.apply {
            adapter = dataAdapter
            layoutManager = LinearLayoutManager(this@ImageActivity)

            realm.where(ImageModel::class.java).findAll().let {
                dataAdapter.submitList(it)
            }
            }


    }

    fun clickDelete() {
        dataAdapter.setOnItemClickListener {
            viewModel.deleteData(it)
        }
    }

    fun editTextZone() {
        et_search.apply {
            setOnEditorActionListener { _, actionId, _ ->
                val textInEditText = text.toString()
                if (actionId == EditorInfo.IME_ACTION_SEARCH && textInEditText.isNotEmpty()) {
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
                    hideProgressBar()
                    response.data?.let { response ->
                        val imageUrl =
                            response.data[(0..response.data.size).random()].images.downsizedMedium.url
                        Log.d(TAG, "image url ${response.data.size}")

                        Glide.with(this)
                            .asGif()
                            .load(imageUrl)
                            .into(iv_image)

                        if (realm.where(ImageModel::class.java).equalTo("url", imageUrl).findFirst()
                                .toString() != imageUrl
                        ) {
                            viewModel.saveData(imageUrl,et_search.text.toString())

                        }

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "An error fetchList: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        progress_bar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

}
