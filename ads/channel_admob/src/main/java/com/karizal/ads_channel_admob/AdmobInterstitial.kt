package com.karizal.ads_channel_admob

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.karizal.ads_base.AdsBaseConst
import com.karizal.ads_base.contract.InterstitialContract


class AdmobInterstitial(private val data: AdmobData) : InterstitialContract {
    override val name: String = AdsBaseConst.admob
    override var isDebug: Boolean = false
    override var activity: Activity? = null
    override var onInitializeOK: (name: String) -> Unit = {}
    override var onInitializeError: (name: String) -> Unit = {}
    private var interstitial: InterstitialAd? = null

    override fun initialize(
        activity: Activity,
        isDebug: Boolean,
        onInitializeOK: (name: String) -> Unit,
        onInitializeError: (name: String) -> Unit
    ) {
        super.initialize(activity, isDebug, onInitializeOK, onInitializeError)
        var adRequestBuilder = AdRequest.Builder()
        data.keywords.forEach {
            adRequestBuilder = adRequestBuilder.addKeyword(it)
        }
        val adRequest = adRequestBuilder.build()

        val adUnitId = if (isDebug) {
            AdmobConst.ADMOB_INTERSTITIAL_UNIT_ID_DEBUG
        } else {
            data.interstitial_id ?: return onInitializeError.invoke(name)
        }

        InterstitialAd.load(activity, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                interstitial = null
                this@AdmobInterstitial.onInitializeError.invoke(name)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                interstitial = interstitialAd
                this@AdmobInterstitial.onInitializeOK.invoke(name)
            }

        })
    }

    override fun show(
        activity: Activity,
        possibleToShow: (channel: String) -> Boolean,
        onHide: () -> Unit,
        onFailure: (activity: Activity) -> Unit
    ) {
        if (possibleToShow.invoke(name).not()) {
            return onFailure.invoke(activity)
        }

        val admobListener = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                onHide.invoke()
                initialize(activity, isDebug, onInitializeOK, onInitializeError)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                onFailure.invoke(activity)
            }


            override fun onAdShowedFullScreenContent() {

            }
        }

        interstitial?.fullScreenContentCallback = admobListener
        interstitial?.show(activity)
    }
}