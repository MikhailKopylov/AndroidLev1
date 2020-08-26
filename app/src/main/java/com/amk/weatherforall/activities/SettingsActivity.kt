package com.amk.weatherforall.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import com.amk.weatherforall.R
import com.amk.weatherforall.core.SettingsPresenter

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        selectTemperatureMode()

        val showWindCheckBox: CheckBox = findViewById(R.id.show_wind_checkBox)
        showWindCheckBox.isChecked =
            SettingsPresenter.isShowWind

        val showPressureCheckBox: CheckBox = findViewById(R.id.show_pressure_checkBox)
        showPressureCheckBox.isChecked =
            SettingsPresenter.isShowPressure

        Log.d("SettingsActivity", "onCreate")
    }

    @SuppressLint("SetTextI18n")
    private fun selectTemperatureMode() {
        //TODO add change temperature mode
//        val temperatureTextView: TextView = findViewById(R.id.temperature_text_view)
        val temperatureC: RadioButton = findViewById(R.id.display_in_C_radioButton)
        val temperatureF: RadioButton = findViewById(R.id.display_in_F_radioButton)
        val temperatureRadioGroup: RadioGroup =
            findViewById(R.id.select_temperature_mode_radio_button)

        if (SettingsPresenter.isShowTemperatureInC) {
            temperatureC.isChecked = true
        } else {
            temperatureF.isChecked = true
        }

        temperatureRadioGroup.setOnCheckedChangeListener { _, radioButtonId ->
            when (radioButtonId) {
                temperatureC.id -> SettingsPresenter.isShowTemperatureInC = true
                temperatureF.id -> SettingsPresenter.isShowTemperatureInC = false
            }
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d("SettingsActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("SettingsActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        val showWindCheckBox: CheckBox = findViewById(R.id.show_wind_checkBox)
        SettingsPresenter.isShowWind = showWindCheckBox.isChecked

        val showPressureCheckBox: CheckBox = findViewById(R.id.show_pressure_checkBox)
        SettingsPresenter.isShowPressure = showPressureCheckBox.isChecked
        Log.d("SettingsActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("SettingsActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SettingsActivity", "onDestroy")
    }
}