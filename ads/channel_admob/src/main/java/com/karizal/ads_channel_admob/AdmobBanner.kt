package com.karizal.ads_channel_admob

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.karizal.ads_base.AdsBaseConst
import com.karizal.ads_base.contract.BannerContract


class AdmobBanner(
    private val data: AdmobData
) : BannerContract {
    override val name: String = AdsBaseConst.admob
    override var isDebug: Boolean = false
    override var activity: Activity? = null

    @SuppressLint("MissingPermission")
    override fun fetch(
        container: ViewGroup,
        preparing: () -> Unit,
        possibleToLoad: () -> Boolean,
        onSuccessLoaded: (channel: String) -> Unit,
        onFailedLoaded: () -> Unit
    ) {
        with(activity ?: return) {
            prepareContainerView(container)
            preparing.invoke()

            val banner = AdView(this)
            var adBuilder = AdRequest.Builder()
            data.keywords.forEach {
                adBuilder = adBuilder.addKeyword(it)
            }

            val adRequest = adBuilder.build()
            banner.setAdSize(getAdSize(this, container))

            if (isDebug) {
                banner.adUnitId = AdmobConst.ADMOB_BANNER_UNIT_ID_DEBUG
            } else {
                banner.adUnitId = data.banner_id ?: return onFailedLoaded.invoke()
            }

            banner.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    onSuccessLoaded.invoke(name)
                    Log.i(this@AdmobBanner.getClassName(), "Admob.banner.onAdLoaded")
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    if (possibleToLoad.invoke()) {
                        hideView(container)
                        onFailedLoaded.invoke()
                    }
                    Log.i(this@AdmobBanner.getClassName(), "Admob.banner.onAdFailedToLoad")
                }

                override fun onAdOpened() {
                    Log.i(this@AdmobBanner.getClassName(), "Admob.banner.onAdOpened")
                }

                override fun onAdClicked() {
                    Log.i(this@AdmobBanner.getClassName(), "Admob.banner.onAdClicked")
                }

                override fun onAdClosed() {
                    Log.i(this@AdmobBanner.getClassName(), "Admob.banner.onAdClosed")
                }
            }

            if (possibleToLoad()) {
                container.removeAllViewsInLayout()
                container.addView(banner)
                banner.loadAd(adRequest)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun getAdSize(context: Context, container: ViewGroup): AdSize {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = (context as Activity).windowManager.currentWindowMetrics
            val bounds = windowMetrics.bounds

            var adWidthPixels = container.width.toFloat()

            // If the ad hasn't been laid out, default to the full screen width.
            if (adWidthPixels == 0f) {
                adWidthPixels = bounds.width().toFloat()
            }

            val density = context.resources.displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()

            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
        } else {
            val display = (context as Activity).windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)
            val widthPixels = outMetrics.widthPixels.toFloat()
            val density = outMetrics.density

            val adWidth = (widthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                context,
                adWidth
            )
        }
    }
}