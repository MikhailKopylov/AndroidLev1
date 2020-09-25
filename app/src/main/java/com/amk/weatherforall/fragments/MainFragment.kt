package com.amk.weatherforall.fragments

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amk.weatherforall.R
import com.amk.weatherforall.activities.CoordinatorActivity
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Constants
import com.amk.weatherforall.core.Constants.CITY_NAME
import com.amk.weatherforall.core.Weather.WeatherForecast
import com.amk.weatherforall.core.WeatherPresenter
import com.amk.weatherforall.core.interfaces.ObservableWeather
import com.amk.weatherforall.core.interfaces.PublisherWeather
import com.amk.weatherforall.core.interfaces.PublisherWeatherGetter
import com.amk.weatherforall.core.interfaces.StartFragment
import com.amk.weatherforall.fragments.dialogs.NoNetworkDialog
import com.amk.weatherforall.fragments.dialogs.OnDialogListener
import com.amk.weatherforall.services.WeatherRequestService


class MainFragment : Fragment(), ObservableWeather {

    companion object {
        fun getInstance(): MainFragment {
            return MainFragment()
        }

        const val BROADCAST_ACTION_REQUEST_FINISHED =
            "com.amk.weatherforall.services.WeatherRequest.finished"
    }

    private val handler: Handler = Handler()

    private lateinit var cityTextView: TextView
    private var city: City = WeatherPresenter.city

    private var showTemperatureInF: Boolean = false
    private var isNotWindVisible: Boolean = false
    private var isNotPressureVisible: Boolean = false

    private lateinit var fragmentView: View

    private lateinit var weatherForecast: WeatherForecast
    private val weatherPresenter: WeatherPresenter = WeatherPresenter

    lateinit var publisherWeather: PublisherWeather
    lateinit var recyclerView: RecyclerView

    private val onDialogListener: OnDialogListener = object : OnDialogListener {
        override fun onDialogReconnect() {
//            WeatherRequestService.newRequest(city)
            city = WeatherPresenter.city
            WeatherRequestService.startWeatherRequestService(activity, city.name)
//            updateWeather(weatherForecast)
        }

        override fun onDialogCancel() {

        }

    }

    private val requestWeatherReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val cityName: String =
                intent?.getStringExtra(WeatherRequestService.EXTRA_RESULT) ?: return
            city = City(cityName)
            handler.post {
                weatherForecast = WeatherPresenter.weatherForecast
                update(fragmentView)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        publisherWeather = (context as PublisherWeatherGetter).publisherWeather()
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

        initNotificationChannel()

        city = WeatherPresenter.city
        WeatherRequestService.startWeatherRequestService(activity, city.name)
        weatherForecast = WeatherPresenter.weatherForecast
        fragmentView = view
        cityTextView = view.findViewById(R.id.location_text_view)

        val additionalInformationButton: Button =
            view.findViewById(R.id.additional_information_button)
        additionalInformationButton.setOnClickListener {
            val uri: Uri = Uri.parse(resources.getString(R.string.defaultUrl))
            val browser = Intent(Intent.ACTION_VIEW, uri)
            startActivity(browser)
        }
        clickSettings(view)

        cityTextView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(CITY_NAME, cityTextView.text.toString())
            arguments = bundle
            (context as StartFragment).runFragments(FragmentsNames.SelectCityFragment, bundle)
        }

        nextWeathersCreate(view)
        update(view)
    }

    override fun onStart() {
        super.onStart()
        activity?.registerReceiver(
            requestWeatherReceiver, IntentFilter(
                BROADCAST_ACTION_REQUEST_FINISHED
            )
        )
    }

    override fun onResume() {
        super.onResume()
//        WeatherPresenter.newRequest(city)
        city = WeatherPresenter.city
        WeatherRequestService.startWeatherRequestService(activity, city.name)
//        update(view = fragmentView)
    }

    override fun onPause() {
        super.onPause()
        weatherPresenter.historyWeatherQueries.add(weatherForecast)
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(requestWeatherReceiver)
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
                publisherWeather.notify(weatherForecast)
            }
        })
    }

    private fun clickSettings(view: View) {
        val settingsButton: ImageButton = view.findViewById(R.id.settings_button)
        settingsButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(Constants.SETTING_SHOW_MODE_TEMPERATURE, showTemperatureInF)
            bundle.putBoolean(Constants.SETTING_SHOW_PRESSURE, isNotPressureVisible)
            bundle.putBoolean(Constants.SETTING_SHOW_WIND, isNotWindVisible)
            arguments = bundle
            (context as StartFragment).runFragments(FragmentsNames.SettingsFragment, bundle)
        }
    }


    private fun temperatureMode(showInF: Boolean): String {
        return if (!showInF) {
            "${weatherForecast.list[0].main.temp.toInt()} C"
        } else {
            "${weatherForecast.list[0].main.temp.toInt().convertToF()} F"
        }

    }

    @SuppressLint("SetTextI18n")
    private fun update(view: View) {
        val dateTextView: TextView = view.findViewById(R.id.date_text_view)
        val timeTextView: TextView = view.findViewById(R.id.time_text_view)

        (recyclerView.adapter as NextWeatherAdapter).notifyDataSetChanged()

        showTemperatureInF =
            arguments?.getBoolean(Constants.SETTING_SHOW_MODE_TEMPERATURE) ?: showTemperatureInF
        val temperatureTextView: TextView = view.findViewById(R.id.temperature_text_view)
        temperatureTextView.text = temperatureMode(showTemperatureInF)


        isNotPressureVisible =
            arguments?.getBoolean(Constants.SETTING_SHOW_PRESSURE) ?: isNotPressureVisible
        val pressureTextView: TextView = view.findViewById(R.id.pressure_textView)
        if (!isNotPressureVisible) {
            pressureTextView.text = "${weatherForecast.list[0].main.pressure} mm Hg"
            pressureTextView.visibility = View.VISIBLE
        } else {
            pressureTextView.visibility = View.INVISIBLE
        }

        isNotWindVisible = arguments?.getBoolean(Constants.SETTING_SHOW_WIND) ?: isNotWindVisible
        val windTextView: TextView = view.findViewById(R.id.wind_textView)
        if (!isNotWindVisible) {
            windTextView.text = "${weatherForecast.list[0].wind.speed} m/s"
            windTextView.visibility = View.VISIBLE
        } else {
            windTextView.visibility = View.INVISIBLE
        }

//        val cityName: String = arguments?.getString(CITY_NAME) ?: cityTextView.text.toString()
        cityTextView.text = city.name
//        city = City(cityName)
        (activity as? CoordinatorActivity)?.setTitle(city.name)
    }


    private fun Int.convertToF() = ((this * 1.8) + 32).toInt()


    override fun updateWeather(weatherForecast: WeatherForecast?) {
        if (weatherForecast != null) {
            this.weatherForecast = weatherForecast
            update(fragmentView)
            nextWeathersCreate(fragmentView)
        } else {
            val noConnectDialog: NoNetworkDialog = NoNetworkDialog.newInstance()
            noConnectDialog.onDialogListener = onDialogListener
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