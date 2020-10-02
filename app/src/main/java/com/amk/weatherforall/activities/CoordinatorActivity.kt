package com.amk.weatherforall.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.amk.weatherforall.R
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.PublisherWeatherImpl
import com.amk.weatherforall.core.WeatherPresenter
import com.amk.weatherforall.core.database.CityDatabase
import com.amk.weatherforall.core.interfaces.*
import com.amk.weatherforall.fragments.FragmentsNames
import com.amk.weatherforall.services.getUrlByCity
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso

class CoordinatorActivity : AppCompatActivity(), PublisherWeatherGetter, StartFragment, UpdateImage,
    NavigationView.OnNavigationItemSelectedListener {


    private val publisherWeather: PublisherWeather = PublisherWeatherImpl()
    lateinit var drawer: DrawerLayout
    private val onNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_home -> {
                    runFragments(FragmentsNames.MainFragment, Bundle())
                    setSelectItem(item)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_settings -> {
                    runFragments(FragmentsNames.SettingsFragment, Bundle())
                    setSelectItem(item)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_change_city -> {
                    runFragments(FragmentsNames.SelectCityFragment, Bundle())
                    setSelectItem(item)
                    return@OnNavigationItemSelectedListener true
                }
                else -> return@OnNavigationItemSelectedListener false
            }
        }

    lateinit var db: CityDatabase

    private fun setSelectItem(item: MenuItem) {
        val bottomNavView: BottomNavigationView = findViewById(R.id.nav_view)

        for (i in 0 until bottomNavView.menu.size()) {
            item.isChecked = item == bottomNavView.menu.getItem(i)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)
        setSupportActionBar(findViewById(R.id.toolbar))
        val bottomNavView: BottomNavigationView = findViewById(R.id.nav_view)
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

        db = Room.databaseBuilder<CityDatabase>(
            applicationContext, CityDatabase::class.java, "city_database"
        ).allowMainThreadQueries()
            .build()


        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)

        bottomNavView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        runFragments(FragmentsNames.MainFragment, Bundle())

        updateImage(WeatherPresenter.city)
    }

    override fun updateImage(city: City) {
        val backDropImageView: ImageView = findViewById(R.id.city_backdrop)
        Picasso.get()
            .load(getUrlByCity(city))
            .into(backDropImageView)
    }


    override fun runFragments(fragmentName: FragmentsNames, arguments: Bundle) {

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

    fun setTitle(title: String) {
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                runFragments(FragmentsNames.MainFragment, Bundle())
            }

            R.id.settings -> {
                runFragments(FragmentsNames.SettingsFragment, Bundle())
            }

            R.id.select_city -> {
                runFragments(FragmentsNames.SelectCityFragment, Bundle())
            }
        }
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen((GravityCompat.START))) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}