package com.pavelrukin.imagesearch.di

import com.pavelrukin.imagesearch.ImageSearchApp
import com.pavelrukin.imagesearch.repository.ImageRepository
import com.pavelrukin.imagesearch.ui.ImageViewModel
import com.pavelrukin.imagesearch.repository.RealmRepository
import org.koin.android.ext.koin.androidApplication

import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val    imageViewModel = module {
    viewModel { ImageViewModel(get(),get(),androidApplication() as ImageSearchApp) }
    single { ImageRepository() }
    single { RealmRepository() }
}
