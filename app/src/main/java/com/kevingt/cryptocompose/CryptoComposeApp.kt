package com.kevingt.cryptocompose

import android.app.Application
import com.kevingt.cryptocompose.data.CryptoRepo
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class CryptoComposeApp : Application() {

    @Inject
    lateinit var cryptoRepo: CryptoRepo

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}