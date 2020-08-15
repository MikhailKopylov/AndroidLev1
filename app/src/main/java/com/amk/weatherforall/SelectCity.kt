package com.amk.weatherforall

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class SelectCity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)

        selectCityName()
    }

    private fun selectCityName() {
        val writeCityNameEditText: EditText = findViewById(R.id.writeCityNameEditText)
        writeCityNameEditText.visibility = View.INVISIBLE

        val selectCityRadioGroup: RadioGroup = findViewById(R.id.select_city_radioGroup)
        selectCityRadioGroup.setOnCheckedChangeListener { _, radioButtonId ->
            when (radioButtonId) {
                R.id.city_manually_radioButton -> {
                    writeCityNameEditText.visibility = View.VISIBLE
                }
                else -> {
                    writeCityNameEditText.visibility = View.INVISIBLE
                }
            }
        }
    }
}