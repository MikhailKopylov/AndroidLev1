package com.amk.weatherforall

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amk.weatherforall.core.PublisherWeatherImpl
import com.amk.weatherforall.core.interfaces.ObservableWeather
import com.amk.weatherforall.core.interfaces.PublisherWeather
import com.amk.weatherforall.core.interfaces.PublisherWeatherGetter
import com.amk.weatherforall.core.interfaces.StartFragment
import com.amk.weatherforall.fragments.FragmentsNames
import com.amk.weatherforall.fragments.MainFragment

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

    fun setTitle(title:String){
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
    }
}