package com.pavelrukin.imagesearch

import android.app.Application
import com.pavelrukin.imagesearch.di.imageViewModel
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.logging.Level


class ImageSearchApp: Application() {
        override fun onCreate() {
            super.onCreate()
            startKoin {
                androidLogger(org.koin.core.logger.Level.ERROR)
                androidContext(this@ImageSearchApp)
                modules(
                    listOf(
                        imageViewModel
                    )
                )
            }

            Realm.init(this)
            Realm.setDefaultConfiguration(RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build())
        }
    }


