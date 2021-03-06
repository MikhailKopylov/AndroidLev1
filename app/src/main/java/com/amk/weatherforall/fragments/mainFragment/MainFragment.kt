package com.amk.weatherforall.fragments.mainFragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.amk.weatherforall.R
import com.amk.weatherforall.activities.CoordinatorActivity
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.WeatherPresenter
import com.amk.weatherforall.core.database.CityDatabase
import com.amk.weatherforall.core.database.CitySource
import com.amk.weatherforall.core.interfaces.FragmentWeather
import com.amk.weatherforall.core.weather.WeatherForecast
import com.amk.weatherforall.fragments.dialogs.NoNetworkDialog
import com.amk.weatherforall.fragments.dialogs.OnDialogReconnectListener
import com.amk.weatherforall.services.Settings
import com.amk.weatherforall.viewModels.SelectCityViewModel
import com.amk.weatherforall.viewModels.SettingViewModel
import com.amk.weatherforall.viewModels.UpdateCityViewModel
import kotlinx.android.synthetic.main.activity_coordinator.*


class MainFragment : Fragment(), FragmentWeather {

    companion object {
        fun getInstance(): MainFragment {
            return MainFragment()
        }

        const val LOAD_DATA: String = "Load data..."
        const val LOAD_DATA_ID: Int = -1
    }

    private var city: City = City(LOAD_DATA, LOAD_DATA_ID)
    private lateinit var citySource: CitySource

    private var settings = Settings

    private lateinit var fragmentView: View

    private var weatherForecast: WeatherForecast = WeatherPresenter.weatherForecast
    private val weatherPresenter: WeatherPresenter = WeatherPresenter

    private lateinit var recyclerView: RecyclerView

    private lateinit var modelCity: SelectCityViewModel
    private lateinit var updateCity: UpdateCityViewModel
    private lateinit var changeSettings: SettingViewModel


    private val onDialogReconnectListener: OnDialogReconnectListener =
        object : OnDialogReconnectListener {
            override fun onDialogReconnect() {
                city = WeatherPresenter.city
                WeatherPresenter.newRequest(city, resources.getString(R.string.Local))
                updateCity.updateCity(city)
            }

            override fun onDialogCancel() {

            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        WeatherPresenter.fragment = this
        val db: CityDatabase? = activity?.applicationContext?.let {
            Room.databaseBuilder<CityDatabase>(
                it, CityDatabase::class.java, "city_database"
            ).allowMainThreadQueries()
                .build()
        }
        if (db != null) {
            citySource = CitySource(cityDAO = db.cityDAO())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view


        initViewModel()

        updateWeather(WeatherPresenter.weatherForecast)
        nextWeathersCreate(view)
//        checkRequest()
//
        initNotificationChannel()

    }

    private fun initViewModel() {
        modelCity =
            ViewModelProviders.of(activity ?: return).get(SelectCityViewModel::class.java)
        modelCity.selectedCity.observe(viewLifecycleOwner, Observer<City> {
            WeatherPresenter.newRequest(it, resources.getString(R.string.Local))

        })

        changeSettings = ViewModelProviders.of(activity ?: return).get(SettingViewModel::class.java)
        changeSettings.settings.observe(viewLifecycleOwner, Observer<Settings> {
            settings = it
//            checkRequest()
        })

        updateCity = ViewModelProviders.of(activity ?: return).get(UpdateCityViewModel::class.java)


    }

    override fun onPause() {
        super.onPause()
        weatherPresenter.historyWeatherQueries.add(weatherForecast)
    }

    private fun nextWeathersCreate(view: View) {
//        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
//        swipeRefreshLayout.setOnRefreshListener {
//            WeatherPresenter.newRequest(city)
//        }
//        weatherForecast = WeatherPresenter.weatherForecast
//        city = WeatherPresenter.city
        recyclerView = view.findViewById(R.id.nextWeather_view)
//        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = linearLayoutManager

        val nextWeatherAdapter = ListWeatherAdapter(weatherForecast.list, resources)
        recyclerView.adapter = nextWeatherAdapter

//        recyclerView.smoothScrollToPosition(0)


//        nextWeatherAdapter.setOnItemClickListener(object :
//            ListWeatherAdapter.onWeatherItemClickListener {
//            override fun onItemClickListener(view: View, position: Int) {
//                Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show()
//            }
//        })
    }


//    @SuppressLint("SetTextI18n")
//    private fun checkRequest() {
//        if (!WeatherPresenter.isRequestSuccessful) {
//            updateWeather(null)
//        }
//    }

    override fun updateWeather(weatherForecast: WeatherForecast?) {
        if (weatherForecast != null) {
            this.weatherForecast = weatherForecast
            city = weatherForecast.city
            citySource.addCity(city)
            nextWeathersCreate(fragmentView)
            updateCity.updateCity(city)
            if (activity is CoordinatorActivity) {
                (activity as CoordinatorActivity).swipeRefresh.isRefreshing = false
            }
        } else {
            val noConnectDialog: NoNetworkDialog = NoNetworkDialog.newInstance()
            noConnectDialog.onDialogReconnectListener = onDialogReconnectListener
            activity?.supportFragmentManager?.let { noConnectDialog.show(it, "Dialog") }
        }
    }

    // На Андроидах версии 26 и выше необходимо создавать канал уведомлений
    // На старых версиях канал создавать не надо
    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(
                "2", "name", importance
            )
            notificationManager.createNotificationChannel(mChannel)
        }
    }


}