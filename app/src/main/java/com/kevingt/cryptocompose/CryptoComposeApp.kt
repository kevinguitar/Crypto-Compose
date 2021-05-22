package com.kevingt.cryptocompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CryptoComposeApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}