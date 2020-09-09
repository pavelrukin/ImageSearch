package com.pavelrukin.imagesearch.ui

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


        setupRecyclerView()
      //  deleteData()
        clickDelete()
        realmChangeListener()


        editTextZone()
        fetchList()


    }

    fun realmChangeListener() {
        realmListener = RealmChangeListener {
            setupRecyclerView()

            clickDelete()
        }
        realm.addChangeListener(realmListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
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
            realm.beginTransaction()
            try {
                realm.where(ImageModel::class.java).equalTo("url", it.url).findFirst().let {
                    it?.deleteFromRealm()
                }
            } catch (e: RealmException) {
                Log.d(TAG, "onDeleted Item: ${e.message}")
            }
            realm.commitTransaction()
        }

    }


    fun saveData(searchResponse: String, imageUrl: String) {

        realm.beginTransaction()
        try {
            val result: ImageModel? = realm.createObject(ImageModel::class.java)
            result?.searchText = searchResponse
            result?.url = imageUrl
            Log.d(TAG, "saveDatasucces: $searchResponse ====$imageUrl  ")
        } catch (e: RealmException) {
            Log.d(TAG, "saveData: ${e.message}")
        }
        realm.commitTransaction()


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
                            saveData(et_search.text.toString(), imageUrl)
                        }

                    }
                }
                is Resource.Error -> {

                    response.message?.let { message ->

                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "An error fetchList: $message")
                    }
                }
                is Resource.Loading -> {

                }
            }
        })
    }


}
