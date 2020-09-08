package com.pavelrukin.imagesearch.di

import com.pavelrukin.imagesearch.ui.ImageRepository
import com.pavelrukin.imagesearch.ui.ImageViewModel

import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val    imageViewModel = module {
    viewModel { ImageViewModel(get()) }
    single { ImageRepository() }
}
