package com.example.ads

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.example.BuildConfig

object AdManager {
    private const val TAG = "AdManager"
    
    // Test ID: ca-app-pub-3940256099942544/6300978111
    // Your Release ID: ca-app-pub-4837107463343724/5776999492
    
    const val REAL_BANNER_ID = "ca-app-pub-4837107463343724/5776999492"
    const val TEST_BANNER_ID = "ca-app-pub-3940256099942544/6300978111"

    fun getBannerAdUnitId(): String {
        return if (BuildConfig.DEBUG) {
            TEST_BANNER_ID
        } else {
            REAL_BANNER_ID
        }
    }
}
