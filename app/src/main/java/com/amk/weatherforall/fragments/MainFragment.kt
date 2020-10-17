package com.amk.weatherforall.fragments

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amk.weatherforall.R
import com.amk.weatherforall.activities.CoordinatorActivity
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Weather.WeatherForecast
import com.amk.weatherforall.core.WeatherPresenter
import com.amk.weatherforall.core.database.CitySource
import com.amk.weatherforall.fragments.dialogs.NoNetworkDialog
import com.amk.weatherforall.fragments.dialogs.OnDialogReconnectListener
import com.amk.weatherforall.services.Settings
import com.amk.weatherforall.viewModels.SelectCityViewModel
import com.amk.weatherforall.viewModels.SettingViewModel
import com.amk.weatherforall.viewModels.UpdateCityViewModel


class MainFragment : Fragment(){

    companion object {
        fun getInstance(): MainFragment {
            return MainFragment()
        }

        const val LOAD_DATA:String = "Load data..."
    }

    private var city: City = City(LOAD_DATA)
    private lateinit var citySource: CitySource

    private var settings = Settings

    private lateinit var fragmentView: View

    private var weatherForecast: WeatherForecast= WeatherPresenter.weatherForecast
    private val weatherPresenter: WeatherPresenter = WeatherPresenter

    lateinit var recyclerView: RecyclerView

    private lateinit var modelCity: SelectCityViewModel
    private lateinit var updateCity: UpdateCityViewModel
    private lateinit var changeSettings:SettingViewModel


    private val onDialogReconnectListener: OnDialogReconnectListener = object : OnDialogReconnectListener {
        override fun onDialogReconnect() {
            city = WeatherPresenter.city
            WeatherPresenter.newRequest(city)
            updateCity.updateCity(city)
        }

        override fun onDialogCancel() {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        WeatherPresenter.fragment = this
        citySource = CitySource((activity as CoordinatorActivity).db.cityDAO())
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

        if (city.name == LOAD_DATA) {
            WeatherPresenter.newRequest(WeatherPresenter.city)
        }

        initViewModel()

        nextWeathersCreate(view)
        update(view)

        initNotificationChannel()
    }

    private fun initViewModel() {
        modelCity =
            ViewModelProviders.of(activity ?: return).get(SelectCityViewModel::class.java)
        modelCity.selectedCity.observe(viewLifecycleOwner, Observer<City> {
                WeatherPresenter.newRequest(it)

        })

        changeSettings = ViewModelProviders.of(activity?:return).get(SettingViewModel::class.java)
        changeSettings.settings.observe(viewLifecycleOwner, Observer <Settings>{
            settings = it
            update(fragmentView)
        })

        updateCity = ViewModelProviders.of(activity?: return).get(UpdateCityViewModel::class.java)


    }

    override fun onPause() {
        super.onPause()
        weatherPresenter.historyWeatherQueries.add(weatherForecast)
    }

    private fun nextWeathersCreate(view: View) {
        recyclerView = view.findViewById(R.id.nextWeather_view)
        val linearLayoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = linearLayoutManager

        val nextWeatherAdapter = NextWeatherAdapter(weatherForecast.list)
        recyclerView.adapter = nextWeatherAdapter


        nextWeatherAdapter.setOnItemClickListener(object :
            NextWeatherAdapter.onWeatherItemClickListener {
            override fun onItemClickListener(view: View, position: Int) {
                Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show()
            }
        })
    }





    @SuppressLint("SetTextI18n")
    private fun update(view: View) {
        if(!WeatherPresenter.isRequestSuccessful){
            updateWeather(null)
        }
//        val dateTextView: TextView = view.findViewById(R.id.date_text_view)
//        Settings.dateView(dateTextView, weatherForecast.list[0])
//        val timeTextView: TextView = view.findViewById(R.id.time_text_view)
//        Settings.timeView(timeTextView, weatherForecast.list[0])
//
//        (recyclerView.adapter as NextWeatherAdapter).notifyDataSetChanged()
//
//
//        val temperatureTextView: TextView = view.findViewById(R.id.temperature_text_view)
//        temperatureTextView.text = Settings.temperatureMode(settings.temperatureC, weatherForecast.list[0])
//
//        val pressureTextView: TextView = view.findViewById(R.id.pressure_textView)
//        Settings.pressureView(pressureTextView,weatherForecast.list[0])
//
//
//        val windTextView: TextView = view.findViewById(R.id.wind_textView)
//        Settings.windView(windTextView, weatherForecast.list[0])

        updateCity.updateCity(city)
    }






    fun updateWeather(weatherForecast: WeatherForecast?) {
        if (weatherForecast != null) {
            this.weatherForecast = weatherForecast
            city = weatherForecast.city
            citySource.addCity(city)
            update(fragmentView)
            nextWeathersCreate(fragmentView)
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