package com.karizal.ads_channel_admob_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.karizal.ads_base.AdsBaseConst
import com.karizal.ads_base.unit.BaseUnitBanner
import com.karizal.ads_base.unit.BaseUnitInterstitial
import com.karizal.ads_channel_admob.AdmobBanner
import com.karizal.ads_channel_admob.AdmobInterstitial

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // interstitial
        val btnInterstitial = findViewById<Button>(R.id.btn_interstitial)
        val interstitialAds = BaseUnitInterstitial(
            isEnabled = true,
            isDebug = true,
            key = this::class.java.simpleName,
            arrayOf(
                AdmobInterstitial(Const.admobData),
                // other channel
            )
        )
        interstitialAds.onCreate(this)

        btnInterstitial.setOnClickListener {
            interstitialAds.showForce(this, onHide = {
                Toast.makeText(this, "Hide Ads", Toast.LENGTH_SHORT).show()
            })
        }

        // banner unit
        BaseUnitBanner.init(
            activity = this,
            R.id.ads_banner,
            isDebug = true, // enable test mode
            AdmobBanner(Const.admobData),
            // other channels
            forceAdsChannel = AdsBaseConst.admob
        )
    }
}