package com.example.bike_app.activities

import android.animation.*
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.example.bike_app.R


class LoadingActivity : AppCompatActivity() {

    private var mBlueSkyColor: Int? = null
    private var mSunsetSkyColor: Int? = null
    private var mNightSkyColor: Int? = null
    private var mSunView: View? = null
    private var mSkyView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        mSunView = findViewById<View>(R.id.sun)
        mSkyView = findViewById<View>(R.id.sky)

        val resources: Resources = resources
        mBlueSkyColor = resources.getColor(R.color.blue_sky)
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky)
        mNightSkyColor = resources.getColor(R.color.night_sky)

        startAnimation()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            startAnimation()
        }
    }

    private fun startAnimation(){
        val sunYStart = mSunView!!.top.toFloat()
        val sunYEnd = mSkyView!!.height.toFloat()

        val heightAnimator = ObjectAnimator
            .ofFloat(mSunView!!, "y", sunYStart, sunYEnd)
            .setDuration(3000)
        heightAnimator.interpolator = AccelerateInterpolator()

        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(mSkyView!!, "BackgroundColor", mBlueSkyColor!!, mSunsetSkyColor!!)
            .setDuration(1000)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView!!, "backgroundColor", mSunsetSkyColor!!, mNightSkyColor!!)
            .setDuration(500);
        nightSkyAnimator.setEvaluator(ArgbEvaluator())

        val animatorSet = AnimatorSet()
        animatorSet
            .play(heightAnimator)
            .with(sunsetSkyAnimator)
            .before(nightSkyAnimator)
        animatorSet.start()


        animatorSet.doOnEnd {
            Log.d("debug", "end of animation")
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}