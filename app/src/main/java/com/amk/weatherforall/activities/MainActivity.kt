package com.amk.weatherforall.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.amk.weatherforall.*
import com.amk.weatherforall.core.PublisherWeatherImpl
import com.amk.weatherforall.core.interfaces.ObservableWeather
import com.amk.weatherforall.core.interfaces.PublisherWeather
import com.amk.weatherforall.core.interfaces.PublisherWeatherGetter
import com.amk.weatherforall.core.interfaces.StartFragment
import com.amk.weatherforall.fragments.FragmentsNames
import com.amk.weatherforall.fragments.MainFragment

class MainActivity : AppCompatActivity(), PublisherWeatherGetter, StartFragment {

    private val publisherWeather: PublisherWeather = PublisherWeatherImpl()


    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        runFragments(FragmentsNames.MainFragment,Bundle())
//        val weatherNextFragment:Fragment = FragmentsNames.NextWeathersFragment.fragment


//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.weather_next_day_frame, weatherNextFragment)
//            .addToBackStack("weatherNextFragment")
//            .commit()


        Log.d("MainActivity", "onCreate")
    }

    override fun runFragments(fragmentName: FragmentsNames, arguments:Bundle) {

        val fragment: Fragment = fragmentName.fragment
        fragment.arguments = arguments


        if (fragment is ObservableWeather) {
            publisherWeather.subscribe(fragment)
        }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.weather_today_frame, fragmentName.fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun publisherWeather(): PublisherWeather {
        return publisherWeather
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")
    }

    override fun onStart() {
        super.onStart()

        Log.d("MainActivity", "onStart")
    }





}