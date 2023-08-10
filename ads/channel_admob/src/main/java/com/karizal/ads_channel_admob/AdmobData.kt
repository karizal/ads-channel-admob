package com.karizal.ads_channel_admob

import com.karizal.ads_base.data.BasicAdsData


class AdmobData(
    val app_id: String,
    val banner_id: String? = null,
    val interstitial_id: String? = null,
    val native_id: String? = null,
    val reward_id: String? = null,
    val app_open_id: String? = null,
    val keywords: List<String> = listOf()
) : BasicAdsData(name = "admob") {
    companion object {
        fun test() = AdmobData(
            "ca-app-pub-3940256099942544~3347511713",
            AdmobConst.ADMOB_BANNER_UNIT_ID_DEBUG,
            AdmobConst.ADMOB_INTERSTITIAL_UNIT_ID_DEBUG,
            AdmobConst.ADMOB_NATIVE_UNIT_ID_DEBUG,
            AdmobConst.ADMOB_REWARD_UNIT_ID_DEBUG,
            AdmobConst.ADMOB_APP_OPEN_UNIT_ID_DEBUG
        )
    }
}