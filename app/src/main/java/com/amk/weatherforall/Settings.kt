package com.amk.weatherforall

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        selectTemperatureMode()
    }

    @SuppressLint("SetTextI18n")
    private fun selectTemperatureMode() {
        //TODO add change temperature mode
//        val temperatureTextView: TextView = findViewById(R.id.temperature_text_view)
        val temperatureC: RadioButton = findViewById(R.id.display_in_C_radioButton)
        val temperatureF: RadioButton = findViewById(R.id.display_in_F_radioButton)
        val temperatureRadioGroup: RadioGroup =
            findViewById(R.id.select_temperature_mode_radio_button)

        temperatureRadioGroup.setOnCheckedChangeListener { _, radioButtonId ->
            when (radioButtonId) {
//                temperatureC.id -> temperatureTextView.text = "${MainActivity.TEMPERATURE_C} °C"
//                temperatureF.id -> temperatureTextView.text =
//                    "${MainActivity.TEMPERATURE_C.convertToF()} °F"
            }
        }
    }

    private fun Int.convertToF() = ((this * 1.8) + 32).toInt()
}