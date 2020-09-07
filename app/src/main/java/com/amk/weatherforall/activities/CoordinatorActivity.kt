package com.amk.weatherforall.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amk.weatherforall.R
import com.amk.weatherforall.core.PublisherWeatherImpl
import com.amk.weatherforall.core.interfaces.ObservableWeather
import com.amk.weatherforall.core.interfaces.PublisherWeather
import com.amk.weatherforall.core.interfaces.PublisherWeatherGetter
import com.amk.weatherforall.core.interfaces.StartFragment
import com.amk.weatherforall.fragments.FragmentsNames
import com.amk.weatherforall.fragments.MainFragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class CoordinatorActivity : AppCompatActivity(), PublisherWeatherGetter, StartFragment {

    private lateinit var weatherNextFragment:Fragment

    private val publisherWeather: PublisherWeather = PublisherWeatherImpl()

        val onNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_home -> {
                        runFragments(FragmentsNames.MainFragment, Bundle())
                        return@OnNavigationItemSelectedListener true
                    }

                    R.id.navigation_settings -> {
                        runFragments(FragmentsNames.SettingsFragment, Bundle())
                        return@OnNavigationItemSelectedListener true
                    }

                    R.id.navigation_change_city -> {
                        runFragments(FragmentsNames.SelectCityFragment, Bundle())
                        return@OnNavigationItemSelectedListener true
                    }

//                    R.id.navigation_update -> runFragments(FragmentsNames.SelectCityFragment,Bundle())
                    else -> return@OnNavigationItemSelectedListener false
                }
            }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)
        setSupportActionBar(findViewById(R.id.toolbar))
        val bottomNavView:BottomNavigationView = findViewById(R.id.nav_view)
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

        bottomNavView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        runFragments(FragmentsNames.MainFragment, Bundle())


    }


    override fun runFragments(fragmentName: FragmentsNames, arguments: Bundle) {

        val fragment: Fragment = fragmentName.fragment
        fragment.arguments = arguments

        if (fragment is ObservableWeather) {
            publisherWeather.subscribe(fragment)
        }

//        if(fragment is MainFragment){
//            weatherNextFragment= FragmentsNames.NextWeathersFragment.fragment
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.weather_next_day_frame, weatherNextFragment)
//                .addToBackStack(getString(R.string.weatherNext_Fragment))
//                .commit()
//        } else{
//            if (weatherNextFragment != null){
//                supportFragmentManager
//                    .popBackStack(getString(R.string.weatherNext_Fragment), 0)
//            }
//        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.weather_today_frame, fragmentName.fragment)
            .addToBackStack(null)
            .commit()

    }

    override fun publisherWeather(): PublisherWeather {
        return publisherWeather
    }

    fun setTitle(title: String) {
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
    }
}