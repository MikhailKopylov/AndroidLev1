package com.amk.weatherforall.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.amk.weatherforall.activities.CoordinatorActivity
import com.amk.weatherforall.R
import com.amk.weatherforall.core.Constants
import com.amk.weatherforall.core.Constants.CITY_NAME
import com.amk.weatherforall.core.DateTimeUtils
import com.amk.weatherforall.core.Weather
import com.amk.weatherforall.core.WeatherPresenter
import com.amk.weatherforall.core.interfaces.ObservableWeather
import com.amk.weatherforall.core.interfaces.PublisherWeather
import com.amk.weatherforall.core.interfaces.PublisherWeatherGetter
import com.amk.weatherforall.core.interfaces.StartFragment

class MainFragment(private val nextWeatherList:List<Weather>) : Fragment(), ObservableWeather {
    private lateinit var cityTextView: TextView

    private var showTemperatureInF: Boolean = false
    private var isNotWindVisible: Boolean = false
    private var isNotPressureVisible: Boolean = false

    private lateinit var fragmentView: View

    private val weathers: ArrayList<Weather> = WeatherPresenter.weatherList
    private var weather:Weather

    lateinit var publisherWeather: PublisherWeather

    init {
        weather = 0.getWeather()
    }

    companion object {
        fun getInstance(weathers:List<Weather>): MainFragment {
            return MainFragment(weathers)
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

        update(view)
        nextWeathrsCreate(view)
    }

    private fun nextWeathrsCreate(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.nextWeather_view)
        val linearLayoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = linearLayoutManager

        val nextWeatherAdapter = NextWeatherAdapter(nextWeatherList.toMutableList())
        recyclerView.adapter = nextWeatherAdapter

        nextWeatherAdapter.setOnItemClickListener(object :
            NextWeatherAdapter.onWeatherItemClickListener {
            override fun onItemClickListener(view: View, position: Int) {
                Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show()
                publisherWeather.notify(nextWeatherList[position])
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
            "${weather.temperature} C"
        } else {
            "${weather.temperature.convertToF()} F"
        }

    }

    @SuppressLint("SetTextI18n")
    private fun update(view: View) {
        val dateTextView: TextView = view.findViewById(R.id.date_text_view)
        val timeTextView: TextView = view.findViewById(R.id.time_text_view)
        val updateButton: ImageButton = view.findViewById(R.id.update_button)
        dateTextView.text = DateTimeUtils.formatDate(weather.dateTimeWeather)
        timeTextView.text = DateTimeUtils.formatTime(weather.dateTimeWeather)
//        updateButton.setOnClickListener {
//            weather = weathers[0]
//            update(view)
////            dateTextView.text = DateTimeUtils.formatDate(weather.dateTimeWeather)
//            timeTextView.text = DateTimeUtils.formatTime(weather.dateTimeWeather)

//        }

        showTemperatureInF =
            arguments?.getBoolean(Constants.SETTING_SHOW_MODE_TEMPERATURE) ?: showTemperatureInF
        val temperatureTextView: TextView = view.findViewById(R.id.temperature_text_view)
        temperatureTextView.text = temperatureMode(showTemperatureInF)

        isNotPressureVisible =
            arguments?.getBoolean(Constants.SETTING_SHOW_PRESSURE) ?: isNotPressureVisible
        val pressureTextView: TextView = view.findViewById(R.id.pressure_textView)
        if (!isNotPressureVisible) {
            pressureTextView.text = "${weather.pressure} mm Hg"
            pressureTextView.visibility = View.VISIBLE
        } else {
            pressureTextView.visibility = View.INVISIBLE
        }

        isNotWindVisible = arguments?.getBoolean(Constants.SETTING_SHOW_WIND) ?: isNotWindVisible
        val windTextView: TextView = view.findViewById(R.id.wind_textView)
        if (!isNotWindVisible) {
            windTextView.text = "${weather.wind} m/s"
            windTextView.visibility = View.VISIBLE
        } else {
            windTextView.visibility = View.INVISIBLE
        }

        val cityName:String = arguments?.getString(CITY_NAME) ?: cityTextView.text.toString()
        cityTextView.text = cityName
        (activity as? CoordinatorActivity)?.setTitle(cityName)
    }


    private fun Int.convertToF() = ((this * 1.8) + 32).toInt()

    private fun Int.getWeather():Weather {
        return weathers[this]
    }

    override fun updateWeather(weather: Weather) {
        this.weather = weather
        update(fragmentView)
    }


}