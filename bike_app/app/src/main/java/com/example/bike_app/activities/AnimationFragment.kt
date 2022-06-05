package com.example.bike_app.activities

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bike_app.R

class AnimationFragment : Fragment() {
    private var mSceneView: View? = null
    private var mSunView: View? = null
    private var mSkyView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_animation, container, false)
        mSceneView = view;
        mSunView = view.findViewById<View>(R.id.sun);
        mSkyView = view.findViewById<View>(R.id.sky);
        startAnimation()

        return view
    }

    fun startAnimation(){
        val sunYStart = mSunView!!.top.toFloat()
        val sunYEnd = mSkyView!!.height.toFloat()

        val heightAnimator = ObjectAnimator
            .ofFloat(mSunView!!, "y", sunYStart, sunYEnd)
            .setDuration(3000)
        heightAnimator.start()
    }
}