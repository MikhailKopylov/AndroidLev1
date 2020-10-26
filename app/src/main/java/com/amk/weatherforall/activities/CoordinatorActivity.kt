package com.amk.weatherforall.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.amk.weatherforall.R
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.WeatherPresenter
import com.amk.weatherforall.core.database.CityDatabase
import com.amk.weatherforall.core.database.CitySource
import com.amk.weatherforall.fragments.FragmentsNames
import com.amk.weatherforall.fragments.dialogs.ExitAppDialog
import com.amk.weatherforall.fragments.dialogs.OnDialogListener
import com.amk.weatherforall.fragments.runFragments
import com.amk.weatherforall.services.getUrlByCity
import com.amk.weatherforall.viewModels.BottomNavigationViewModel
import com.amk.weatherforall.viewModels.UpdateCityViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

class CoordinatorActivity : AppCompatActivity()/*,
    NavigationView.OnNavigationItemSelectedListener*/ {


    //    private lateinit var drawer: DrawerLayout
    private lateinit var bottomNavView: BottomNavigationView

    private lateinit var updateCity: UpdateCityViewModel
    private lateinit var selectItemBottomNavView: BottomNavigationViewModel

    lateinit var db: CityDatabase
    private lateinit var citySource: CitySource

    internal var idFragmentVisible = FragmentsNames.MainFragment.idFragment
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


    private fun setSelectItem(item: MenuItem) {
        val bottomNavView: BottomNavigationView = findViewById(R.id.nav_view)

        for (i in 0 until bottomNavView.menu.size()) {
            item.isChecked = item == bottomNavView.menu.getItem(i)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBase()
        initCityList()

        setContentView(R.layout.activity_coordinator)
        setSupportActionBar(findViewById(R.id.toolbar))
        bottomNavView = findViewById(R.id.nav_view)
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

        initSwipeRefresh()

        initViewModel()

//        val navigationView: NavigationView = findViewById(R.id.navigation_view)
//        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//
//        drawer = findViewById(R.id.drawer_layout)
//        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
//        drawer.addDrawerListener(toggle)
//        toggle.syncState()
//        navigationView.setNavigationItemSelectedListener(this)

        bottomNavView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        runFragments(this, FragmentsNames.MainFragment)
        initNotificationChannel()

    }

    private fun initSwipeRefresh() {
        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener {
            if (idFragmentVisible == FragmentsNames.MainFragment.idFragment) {
                WeatherPresenter.newRequest(WeatherPresenter.city)
            } else {
                swipeRefreshLayout.isRefreshing = false
            }

        }

        swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }

    private fun initCityList() {
        if (citySource.allCities.isEmpty() || citySource.allCities.size == 1) {
            citySource.addCity(City(resources.getString(R.string.Moscow), 524901))
            citySource.addCity(City(resources.getString(R.string.Saint_Petersburg), 498817))
            citySource.addCity(City(resources.getString(R.string.Saratov), 498677))
            citySource.addCity(City(resources.getString(R.string.Novosibirsk), 1496747))
            citySource.addCity(City(resources.getString(R.string.Ekaterinburg), 1486209))
            citySource.addCity(City(resources.getString(R.string.Kazan), 551487))
            citySource.addCity(City(resources.getString(R.string.Chelyabinsk), 1508291))
            citySource.addCity(City(resources.getString(R.string.Samara), 499068))
            citySource.addCity(City(resources.getString(R.string.Omsk), 1496153))
            citySource.addCity(City(resources.getString(R.string.Rostov_on_don), 501183))
            citySource.addCity(City(resources.getString(R.string.Ufa), 479561))
            citySource.addCity(City(resources.getString(R.string.Krasnoyarsk), 1502026))
            citySource.addCity(City(resources.getString(R.string.Voronezh), 472045))
            citySource.addCity(City(resources.getString(R.string.Perm), 511196))
            citySource.addCity(City(resources.getString(R.string.Volgograd), 472757))
            citySource.addCity(City(resources.getString(R.string.Krasnodar), 542415))
            citySource.addCity(City(resources.getString(R.string.Tyumen), 1488754))
            citySource.addCity(City(resources.getString(R.string.Izhevsk), 554840))
            citySource.addCity(City(resources.getString(R.string.Barnaul), 1510853))
            citySource.addCity(City(resources.getString(R.string.Ulyanovsk), 479119))
            citySource.addCity(City(resources.getString(R.string.Irkutsk), 2023469))
            citySource.addCity(City(resources.getString(R.string.Khabarovsk), 2022890))
            citySource.addCity(City(resources.getString(R.string.Yaroslavl), 468902))
            citySource.addCity(City(resources.getString(R.string.Vladivostok), 2013348))
            citySource.addCity(City(resources.getString(R.string.Kotlas), 543704))
            citySource.addCity(City(resources.getString(R.string.Vladivostok), 479119))
        }
        WeatherPresenter.city = citySource.allCities[0]
    }

    private fun initDataBase() {
        db = Room.databaseBuilder<CityDatabase>(
            applicationContext, CityDatabase::class.java, "city_database"
        ).allowMainThreadQueries()
            .build()
        citySource = CitySource(db.cityDAO())
    }

    private fun initViewModel() {
        updateCity = ViewModelProviders.of(this).get(UpdateCityViewModel::class.java)
        updateCity.updateCity.observe(this, Observer<City> {
            setTitle(it.name)
            updateImage(it)
        })

        selectItemBottomNavView =
            ViewModelProviders.of(this).get(BottomNavigationViewModel::class.java)
        selectItemBottomNavView.itemBottomNavView.observe(this, Observer<Int> {
            bottomNavView.selectedItemId = it
        })
    }

    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            val importance: Int = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel("2", "name", importance)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun updateImage(city: City) {
        val backDropImageView: ImageView = findViewById(R.id.city_backdrop)
        Picasso.get()
            .load(getUrlByCity(city, this))
            .into(backDropImageView)
    }

    private fun setTitle(title: String) {
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
    }

    /*  override fun onNavigationItemSelected(item: MenuItem): Boolean {
          when (item.itemId) {
              R.id.home -> {
                  runFragments(this, FragmentsNames.MainFragment)
                  setSelectItem(item)
              }

              R.id.settings -> {
                  runFragments(this, FragmentsNames.SettingsFragment)
                  setSelectItem(item)
              }

              R.id.select_city -> {
                  runFragments(this, FragmentsNames.SelectCityFragment)
                  setSelectItem(item)
              }
          }
          val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
          drawer.closeDrawer(GravityCompat.START)
          return true
      }*/

    override fun onBackPressed() {
        val exitAppDialog: ExitAppDialog = ExitAppDialog.newInstance()
        exitAppDialog.onDialogListener = onDialogListener
        supportFragmentManager.let { exitAppDialog.show(it, "Exit Dialog") }
    }

    private val onDialogListener: OnDialogListener = object :
        OnDialogListener {
        override fun onDialogOK() {
            finish()
        }

        override fun onDialogCancel() {

        }
    }
}