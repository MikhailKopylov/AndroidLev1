package com.amk.weatherforall.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.amk.weatherforall.*
import com.amk.weatherforall.core.Constants
import com.amk.weatherforall.core.Constants.Companion.CITY_NAME_SELECT
import com.amk.weatherforall.core.Constants.Companion.REQUEST_CODE_SELECT_CITY
import com.amk.weatherforall.core.Constants.Companion.REQUEST_CODE_SETTING
import com.amk.weatherforall.core.Constants.Companion.SETTING_SHOW_MODE_TEMPERATURE
import com.amk.weatherforall.core.Constants.Companion.SETTING_SHOW_PRESSURE
import com.amk.weatherforall.core.Constants.Companion.SETTING_SHOW_WIND
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity(), Constants {

    private lateinit var cityTextView: TextView
    private var showTemperatureInC: Boolean = true
    private val  TEMPERATURE_C = 15

    private var isWindVisible: Boolean = true
    private var isPressureVisible: Boolean = true

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cityTextView = findViewById(R.id.location_text_view)

        val additionalInformationButton:Button = findViewById(R.id.additional_information_button)
        additionalInformationButton.setOnClickListener{
            val uri:Uri = Uri.parse("${resources.getString(R.string.defaultUrl)}")
            val browser = Intent(Intent.ACTION_VIEW, uri)
            startActivity(browser)
        }
        clickSettingsActivity()

        Log.d("MainActivity", "onCreate")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SELECT_CITY -> {
                val cityName: String = data?.getStringExtra(CITY_NAME_SELECT)
                    ?: resources.getString(R.string.Default_city_name)
                cityTextView.text = cityName
//                update()
            }
            REQUEST_CODE_SETTING -> {
                if (data != null) {
                    showTemperatureInC = data.getBooleanExtra(SETTING_SHOW_MODE_TEMPERATURE, true)
                    isPressureVisible = data.getBooleanExtra(SETTING_SHOW_PRESSURE, true)
                    isWindVisible = data.getBooleanExtra(SETTING_SHOW_WIND, true)
                }

            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
                return
            }
        }
    }

    private fun clickSettingsActivity() {
        val settingsButton: ImageButton = findViewById(R.id.settings_button)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra(SETTING_SHOW_MODE_TEMPERATURE, showTemperatureInC)
            intent.putExtra(SETTING_SHOW_PRESSURE, isPressureVisible)
            intent.putExtra(SETTING_SHOW_WIND, isWindVisible)
            startActivityForResult(intent, REQUEST_CODE_SETTING)
        }
    }
    private fun clickSelectCityActivity(cityName: String) {
        cityTextView.setOnClickListener {
            val intent = Intent(this, SelectCityActivity::class.java)
            intent.putExtra(CITY_NAME_SELECT, cityName)
            startActivityForResult(intent, REQUEST_CODE_SELECT_CITY)
        }
    }

    private fun temperatureMode(showInC:Boolean):String{
        return if (showInC) {
            "$TEMPERATURE_C C"
        } else {
            "${TEMPERATURE_C.convertToF()} F"
        }

    }
    private fun update() {
        val dateTextView: TextView = findViewById(R.id.date_text_view)
        val timeTextView: TextView = findViewById(R.id.time_text_view)
        val updateButton: ImageButton = findViewById(R.id.update_button)
        dateTextView.text = dateNow()
        timeTextView.text = timeNow()
        updateButton.setOnClickListener {
            dateTextView.text = dateNow()
            timeTextView.text = timeNow()
        }

        val temperatureTextView: TextView = findViewById(R.id.temperature_text_view)
        temperatureTextView.text = temperatureMode(showTemperatureInC)

        val pressureTextView: TextView = findViewById(R.id.pressure_textView)
        if (isPressureVisible) {
            pressureTextView.visibility = View.VISIBLE
        } else {
            pressureTextView.visibility = View.INVISIBLE
        }

        val windTextView: TextView = findViewById(R.id.wind_textView)
        if (isWindVisible) {
            windTextView.visibility = View.VISIBLE
        } else {
            windTextView.visibility = View.INVISIBLE
        }
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

    override fun onResume() {
        super.onResume()
        update()
        Log.d("MainActivity", "onResume")
    }

    override fun onStart() {
        super.onStart()
        clickSelectCityActivity(cityTextView.text as String)
        Log.d("MainActivity", "onStart")
    }

    private fun Int.convertToF() = ((this * 1.8) + 32).toInt()

}