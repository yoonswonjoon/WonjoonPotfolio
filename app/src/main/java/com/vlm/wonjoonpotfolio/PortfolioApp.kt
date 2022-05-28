package com.vlm.wonjoonpotfolio

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PortfolioApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "e7637755be0d120fa7bde2428d272f0c")
    }
}