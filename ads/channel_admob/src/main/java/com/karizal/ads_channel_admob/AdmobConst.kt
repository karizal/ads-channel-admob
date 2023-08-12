package com.karizal.ads_channel_admob

import android.content.Context
import com.google.android.gms.ads.MobileAds

object AdmobConst {
    internal const val ADMOB_BANNER_UNIT_ID_DEBUG = "ca-app-pub-3940256099942544/6300978111"
    internal const val ADMOB_INTERSTITIAL_UNIT_ID_DEBUG = "ca-app-pub-3940256099942544/1033173712"
    internal const val ADMOB_NATIVE_UNIT_ID_DEBUG = "ca-app-pub-3940256099942544/2247696110"
    internal const val ADMOB_APP_OPEN_UNIT_ID_DEBUG = "ca-app-pub-3940256099942544/3419835294"
    internal const val ADMOB_REWARD_UNIT_ID_DEBUG = "ca-app-pub-3940256099942544/5224354917"
    internal const val ADMOB_TEST_DEVICE_ID = "D85E9400D65AFD773663ACDB5B635FAB"

    fun init(context: Context) {
        MobileAds.initialize(context) {}
    }
}