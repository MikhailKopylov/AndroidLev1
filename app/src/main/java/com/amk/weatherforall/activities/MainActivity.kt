package com.amk.weatherforall.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amk.weatherforall.*

class MainActivity : AppCompatActivity(){

//    private val publisherWeather: PublisherWeather = PublisherWeatherImpl()


    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        runFragments(this, FragmentsNames.MainFragment)
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