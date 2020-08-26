package com.amk.weatherforall.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.amk.weatherforall.R
import com.amk.weatherforall.core.SelectCityPresenter

class SelectCityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)

        selectCityName()
        Log.d("SelectCityActivity", "onCreate")
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

    override fun onStart() {
        super.onStart()
        Log.d("SelectCityActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("SelectCityActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()

        val writeCityNameEditText: EditText = findViewById(R.id.writeCityNameEditText)

        val selectCityRadioGroup: RadioGroup = findViewById(R.id.select_city_radioGroup)
        val radioButtonId: Int = selectCityRadioGroup.checkedRadioButtonId
        val radioButtonCityName: RadioButton = findViewById(radioButtonId)

        when (radioButtonId) {
            R.id.city_manually_radioButton -> {
                SelectCityPresenter.cityName = writeCityNameEditText.text.toString()
            }
            else -> {
                SelectCityPresenter.cityName = radioButtonCityName.text.toString()
            }
        }

        Log.d("SelectCityActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("SelectCityActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SelectCityActivity", "onDestroy")
    }
}