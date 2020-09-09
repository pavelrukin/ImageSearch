package com.pavelrukin.imagesearch.di

import com.pavelrukin.imagesearch.ui.ImageRepository
import com.pavelrukin.imagesearch.ui.ImageViewModel
import com.pavelrukin.imagesearch.ui.RealmRepository

import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val    imageViewModel = module {
    viewModel { ImageViewModel(get(),get()) }
    single { ImageRepository() }
    single { RealmRepository() }
}
