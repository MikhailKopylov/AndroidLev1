package com.amk.weatherforall.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.room.Room
import com.amk.weatherforall.R
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.database.CityDatabase
import com.amk.weatherforall.core.interfaces.*
import com.amk.weatherforall.fragments.FragmentsNames
import com.amk.weatherforall.fragments.runFragments
import com.amk.weatherforall.services.getUrlByCity
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso

class CoordinatorActivity : AppCompatActivity(), UpdateImage,
    NavigationView.OnNavigationItemSelectedListener {


    private lateinit var drawer: DrawerLayout
    private val onNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_home -> {
                    runFragments(this, FragmentsNames.MainFragment)
                    setSelectItem(item)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_settings -> {
                    runFragments(this, FragmentsNames.SettingsFragment)
                    setSelectItem(item)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_change_city -> {
                    runFragments(this, FragmentsNames.SelectCityFragment)
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

        runFragments(this, FragmentsNames.MainFragment)

//        registerReceiver(changeNetStateReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        initNotificationChannel()

    }

    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            val importance: Int = NotificationManager.IMPORTANCE_LOW
            val channel= NotificationChannel("2", "name", importance)
            notificationManager.createNotificationChannel(channel)
        }
    }

//    private fun initGetToken() {
//        FirebaseInstanceId.getInstance()
//            .instanceId.addOnCompleteListener {
//                run {
//                    if (!it.isSuccessful) {
//                        Log.w("PushMessage", "getInstanceId failed", it.exception)
//                    }
//                    val token: String = it.result?.token ?: "Error"
////                    Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

    override fun updateImage(city: City) {
        val backDropImageView: ImageView = findViewById(R.id.city_backdrop)
        Picasso.get()
            .load(getUrlByCity(city))
            .into(backDropImageView)
    }




//    override fun publisherWeather(): PublisherWeather {
//        return publisherWeather
//    }

    fun setTitle(title: String) {
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                runFragments(this, FragmentsNames.MainFragment)
            }

            R.id.settings -> {
                runFragments(this, FragmentsNames.SettingsFragment)
            }

            R.id.select_city -> {
                runFragments(this, FragmentsNames.SelectCityFragment)
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
