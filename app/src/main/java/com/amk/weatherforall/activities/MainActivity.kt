package com.amk.weatherforall.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.amk.weatherforall.*
import com.amk.weatherforall.core.WeatherPresenter
import com.amk.weatherforall.core.interfaces.StartFragment
import com.amk.weatherforall.fragments.FragmentsNames
import com.amk.weatherforall.fragments.runFragments

class MainActivity : AppCompatActivity(){

//    private val publisherWeather: PublisherWeather = PublisherWeatherImpl()


    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        runFragments(this, FragmentsNames.MainFragment)
        Log.d("MainActivity", "onCreate")
    }

//    fun runFragments(app:FragmentActivity, fragmentName: FragmentsNames) {
//
//        fragmentName.fragment
//                    app.supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.weather_today_frame, fragmentName.fragment)
//                .addToBackStack(null)
//                .commit()
//    }
}