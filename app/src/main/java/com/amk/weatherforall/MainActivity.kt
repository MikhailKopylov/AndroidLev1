package com.amk.weatherforall

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
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

        startSelectCityActivity()
        startSettingsActivity()



        update()

    }

    private fun startSettingsActivity() {
        val settingsButton: ImageButton = findViewById(R.id.settings_button)
        settingsButton.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }
    }

    private fun startSelectCityActivity() {
        val cityTextView: TextView = findViewById(R.id.location_text_view)
        cityTextView.setOnClickListener {
            val intent = Intent(this, SelectCity::class.java)
            startActivity(intent)
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





}