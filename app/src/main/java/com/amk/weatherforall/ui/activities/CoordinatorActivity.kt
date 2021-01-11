package com.amk.weatherforall.ui.activities

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
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
import com.amk.weatherforall.mvp.model.database.CityDatabase
import com.amk.weatherforall.mvp.model.database.CitySource
import com.amk.weatherforall.mvp.model.database.createListCities
import com.amk.weatherforall.mvp.model.entity.City.City
import com.amk.weatherforall.mvp.presenter.Settings
import com.amk.weatherforall.mvp.presenter.WeatherPresenter
import com.amk.weatherforall.mvp.view.CityName
import com.amk.weatherforall.services.getUrlByCity
import com.amk.weatherforall.ui.fragments.FragmentsNames
import com.amk.weatherforall.ui.fragments.dialogs.ExitAppDialog
import com.amk.weatherforall.ui.fragments.dialogs.OnDialogListener
import com.amk.weatherforall.ui.fragments.mainFragment.MainFragment
import com.amk.weatherforall.ui.fragments.runFragments
import com.amk.weatherforall.viewModels.BottomNavigationViewModel
import com.amk.weatherforall.viewModels.UpdateCityViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coordinator.*

class CoordinatorActivity : AppCompatActivity(), CityName {

    companion object {
        const val APP_PREFERENCES = "settings"
    }

    private lateinit var bottomNavView: BottomNavigationView

    private lateinit var updateCity: UpdateCityViewModel
    private lateinit var selectItemBottomNavView: BottomNavigationViewModel
    private lateinit var weatherPresenter: WeatherPresenter


    lateinit var db: CityDatabase
    private lateinit var citySource: CitySource

    internal var idFragmentVisible = FragmentsNames.MainFragment.idFragment
    private val onNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, MainFragment.getInstance())
                        .commit()
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

        val settingsLoad: SharedPreferences = getPreferences(MODE_PRIVATE)
        Settings.temperatureC = settingsLoad.getBoolean(Settings.TEMPERATURE_KEY, true)
        Settings.showPressure = settingsLoad.getBoolean(Settings.PRESSURE_KEY, true)
        Settings.showWind = settingsLoad.getBoolean(Settings.WIND_KEY, true)

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

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, MainFragment.getInstance())
            .commit()

        initNotificationChannel()

    }

    private fun initSwipeRefresh() {
        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener {
            if (idFragmentVisible == FragmentsNames.MainFragment.idFragment) {
                val fragment = supportFragmentManager.findFragmentById(FragmentsNames.MainFragment.idFragment)
                fragment?.let{
                    onResume()
                    weatherPresenter.swipeRefresh(resources.getString(R.string.Local))
                }
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
            createListCities(citySource, resources)
        }
    }


    private fun initDataBase() {
        db = Room.databaseBuilder<CityDatabase>(
            applicationContext, CityDatabase::class.java, "city_database"
        ).allowMainThreadQueries()
            .build()
        citySource = CitySource(db.cityDAO())
    }

    override fun setCityName(cityName: String) {
        updateCity = ViewModelProviders.of(this).get(UpdateCityViewModel::class.java)
        updateCity.updateCity.observe(this, Observer<City> {
            setTitle(it.name)
            updateImage(it)
            swipeRefresh.isRefreshing = false
        })
    }

    private fun initViewModel() {


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

    @SuppressLint("CommitPrefEdits")
    override fun onStop() {
        super.onStop()
        val sharedPref: SharedPreferences = getPreferences(MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(Settings.TEMPERATURE_KEY, Settings.temperatureC)
        editor.putBoolean(Settings.PRESSURE_KEY, Settings.showPressure)
        editor.putBoolean(Settings.WIND_KEY, Settings.showWind)
        editor.apply()
    }

}