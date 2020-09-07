package com.amk.weatherforall

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amk.weatherforall.core.PublisherWeatherImpl
import com.amk.weatherforall.core.interfaces.ObservableWeather
import com.amk.weatherforall.core.interfaces.PublisherWeather
import com.amk.weatherforall.core.interfaces.PublisherWeatherGetter
import com.amk.weatherforall.core.interfaces.StartFragment
import com.amk.weatherforall.fragments.FragmentsNames

class CoordinatorActivity : AppCompatActivity(), PublisherWeatherGetter, StartFragment {

    private val publisherWeather: PublisherWeather = PublisherWeatherImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

        runFragments(FragmentsNames.MainFragment,Bundle())
        val weatherNextFragment: Fragment = FragmentsNames.NextWeathersFragment.fragment

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.weather_next_day_frame, weatherNextFragment)
            .addToBackStack("weatherNextFragment")
            .commit()

    }

    override fun runFragments(fragmentName: FragmentsNames, arguments:Bundle) {

        val weatherTodayFragment: Fragment = fragmentName.fragment
        weatherTodayFragment.arguments = arguments

        if (weatherTodayFragment is ObservableWeather) {
            publisherWeather.subscribe(weatherTodayFragment)
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
}