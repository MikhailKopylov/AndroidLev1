package com.amk.weatherforall.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.amk.weatherforall.R
import com.amk.weatherforall.core.Constants
import com.amk.weatherforall.core.Constants.CITY_NAME
import com.amk.weatherforall.core.interfaces.Observable
import com.amk.weatherforall.core.interfaces.StartFragment
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class MainFragment  : Fragment(), Observable {
    private lateinit var cityTextView: TextView
    private var showTemperatureInC: Boolean = true
    private val TEMPERATURE_C = 15

    private var isWindVisible: Boolean = true
    private var isPressureVisible: Boolean = true

    private lateinit var fragmentView: View

    companion object {
        fun getInstance(): MainFragment {
            return MainFragment()
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
    }

    private fun clickSettings(view: View) {
        val settingsButton: ImageButton = view.findViewById(R.id.settings_button)
        settingsButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(Constants.SETTING_SHOW_MODE_TEMPERATURE, showTemperatureInC)
            bundle.putBoolean(Constants.SETTING_SHOW_PRESSURE, isPressureVisible)
            bundle.putBoolean(Constants.SETTING_SHOW_WIND, isWindVisible)
            arguments = bundle
            (context as StartFragment).runFragments(FragmentsNames.SettingsFragment, bundle)
        }
    }


    private fun temperatureMode(showInC: Boolean): String {
        return if (showInC) {
            "$TEMPERATURE_C C"
        } else {
            "${TEMPERATURE_C.convertToF()} F"
        }

    }

    private fun update(view: View) {
        val dateTextView: TextView = view.findViewById(R.id.date_text_view)
        val timeTextView: TextView = view.findViewById(R.id.time_text_view)
        val updateButton: ImageButton = view.findViewById(R.id.update_button)
        dateTextView.text = dateNow()
        timeTextView.text = timeNow()
        updateButton.setOnClickListener {
            dateTextView.text = dateNow()
            timeTextView.text = timeNow()

        }

        showTemperatureInC =
            arguments?.getBoolean(Constants.SETTING_SHOW_MODE_TEMPERATURE) ?: showTemperatureInC
        val temperatureTextView: TextView = view.findViewById(R.id.temperature_text_view)
        temperatureTextView.text = temperatureMode(showTemperatureInC)

        isPressureVisible =
            arguments?.getBoolean(Constants.SETTING_SHOW_PRESSURE) ?: isPressureVisible
        val pressureTextView: TextView = view.findViewById(R.id.pressure_textView)
        if (isPressureVisible) {
            pressureTextView.visibility = View.VISIBLE
        } else {
            pressureTextView.visibility = View.INVISIBLE
        }

        isWindVisible = arguments?.getBoolean(Constants.SETTING_SHOW_WIND) ?: isWindVisible
        val windTextView: TextView = view.findViewById(R.id.wind_textView)
        if (isWindVisible) {
            windTextView.visibility = View.VISIBLE
        } else {
            windTextView.visibility = View.INVISIBLE
        }

        cityTextView.text = arguments?.getString(CITY_NAME) ?: cityTextView.text
    }

    @SuppressLint("SimpleDateFormat")
    private fun timeNow(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            if (current.minute > 9) {
                "${current.hour}:${current.minute}"
            } else {
                "${current.hour}:0${current.minute}"
            }
        } else {
            val formatterTime = SimpleDateFormat("hh:mma")
            formatterTime.format(Date())
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateNow(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()

            if (current.monthValue > 9) {
                "${current.dayOfMonth}.${current.monthValue}.${current.year}"
            } else {
                "${current.dayOfMonth}.0${current.monthValue}.${current.year}"
            }
        } else {
            val formatterDate = SimpleDateFormat("dd.mm.yyyy")
            formatterDate.format(Date())
        }
    }

    private fun Int.convertToF() = ((this * 1.8) + 32).toInt()

    override fun updateCityName(text: String) {
        cityTextView.text = text
        val bundle = Bundle()
        bundle.putString(CITY_NAME, text)
        arguments = bundle
        update(fragmentView)
    }
}