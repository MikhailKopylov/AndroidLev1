package com.amk.weatherforall.fragments

import androidx.fragment.app.FragmentActivity
import com.amk.weatherforall.R

fun runFragments(app: FragmentActivity, fragmentName: FragmentsNames) {

    fragmentName.fragment
    app.supportFragmentManager
        .beginTransaction()
        .replace(R.id.weather_today_frame, fragmentName.fragment)
        .addToBackStack(null)
        .commit()
}