package com.amk.weatherforall.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import com.amk.weatherforall.R
import com.amk.weatherforall.core.Constants.SETTING_SHOW_MODE_TEMPERATURE
import com.amk.weatherforall.core.Constants.SETTING_SHOW_PRESSURE
import com.amk.weatherforall.core.Constants.SETTING_SHOW_WIND

class SettingsActivity : AppCompatActivity() {

    private var showTemperatureInC: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        selectTemperatureMode()

        val showWindCheckBox: CheckBox = findViewById(R.id.show_wind_checkBox)
        showWindCheckBox.isChecked = intent.getBooleanExtra(SETTING_SHOW_WIND, false)

        val showPressureCheckBox: CheckBox = findViewById(R.id.show_pressure_checkBox)
        showPressureCheckBox.isChecked = intent.getBooleanExtra(SETTING_SHOW_PRESSURE, false)

        requestResult()
        Log.d("SettingsActivity", "onCreate")
    }

    @SuppressLint("SetTextI18n")
    private fun selectTemperatureMode() {
        //TODO add change temperature mode
        showTemperatureInC = intent.getBooleanExtra(SETTING_SHOW_MODE_TEMPERATURE, true)

        val temperatureRadioGroup: RadioGroup = findViewById(R.id.select_temperature_mode_radio_button)
        val temperatureC: RadioButton = findViewById(R.id.display_in_C_radioButton)
        val temperatureF: RadioButton = findViewById(R.id.display_in_F_radioButton)

        if (showTemperatureInC) {
            temperatureC.isChecked = true
        } else {
            temperatureF.isChecked = true
        }

        temperatureRadioGroup.setOnCheckedChangeListener { _, radioButtonId ->
            when (radioButtonId) {
                temperatureC.id -> showTemperatureInC = true
                temperatureF.id -> showTemperatureInC = false
            }
        }
    }

    private fun requestResult() {
        val okButton: Button = findViewById(R.id.ok_button_setting)
        okButton.setOnClickListener {
            val showWindCheckBox: CheckBox = findViewById(R.id.show_wind_checkBox)
            val isShowWind = showWindCheckBox.isChecked

            val showPressureCheckBox: CheckBox = findViewById(R.id.show_pressure_checkBox)
            val isShowPressure = showPressureCheckBox.isChecked

            val intentResult = Intent()
            intentResult.putExtra(SETTING_SHOW_MODE_TEMPERATURE, showTemperatureInC)
            intentResult.putExtra(SETTING_SHOW_WIND, isShowWind)
            intentResult.putExtra(SETTING_SHOW_PRESSURE, isShowPressure)

            setResult(RESULT_OK, intentResult)
            finish()
        }

        val cancelButton: Button = findViewById(R.id.cancel_button_setting)
        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("SettingsActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SettingsActivity", "onDestroy")
    }

//    override fun onStart() {
//        super.onStart()
//        Log.d("SettingsActivity", "onStart")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.d("SettingsActivity", "onResume")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.d("SettingsActivity", "onPause")
//    }
}
