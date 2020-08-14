package com.amk.weatherforall

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val TEMPERATURE_C = 15
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectTemperatureMode()

        update()

    }

    private fun update() {
        val dateTextView: TextView = findViewById(R.id.date_text_view)
        val timeTextView: TextView = findViewById(R.id.time_text_view)
        val updateButton: Button = findViewById(R.id.update_button)
        dateTextView.text = dateNow()
        timeTextView.text = timeNow()
        updateButton.setOnClickListener {
            dateTextView.text = dateNow()
            timeTextView.text = timeNow()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun timeNow(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            "${current.hour}:${current.minute}"
        } else {
            val formatterTime = SimpleDateFormat("hh:mma")
            formatterTime.format(Date())
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateNow(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            "${current.dayOfMonth}.${current.monthValue}.${current.year}"
        } else {
            val formatterDate = SimpleDateFormat("dd.mm.yyyy")
            formatterDate.format(Date())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun selectTemperatureMode() {
        val temperatureTextView: TextView = findViewById(R.id.temperature_text_view)
        val temperatureC: RadioButton = findViewById(R.id.display_in_C_radioButton)
        val temperatureF: RadioButton = findViewById(R.id.display_in_F_radioButton)
        val temperatureRadioButton: RadioGroup =
            findViewById(R.id.select_temperature_mode_radio_button)

        temperatureRadioButton.setOnCheckedChangeListener { _, radioButtonId ->
            when (radioButtonId) {
                temperatureC.id -> temperatureTextView.text = "$TEMPERATURE_C °C"
                temperatureF.id -> temperatureTextView.text =
                    "${TEMPERATURE_C.convertToF()} °F"
            }
        }
    }

    private fun Int.convertToF() = ((this * 1.8) + 32).toInt()

}