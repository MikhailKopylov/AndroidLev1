package com.amk.weatherforall.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.view.children
import com.amk.weatherforall.R
import com.amk.weatherforall.core.Constants
import kotlinx.android.synthetic.main.activity_select_city.*

class SelectCityActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)

        val cityNameBeforeSelect = intent.getStringExtra(Constants.CITY_NAME_SELECT)
            ?:resources.getString(R.string.Default_city_name)

        selectCityName(cityNameBeforeSelect)

        requestResult()
        Log.d("SelectCityActivity", "onCreate")
    }

    private fun requestResult() {
        val okButton: Button = findViewById(R.id.ok_button_select_city)
        okButton.setOnClickListener {
            requestCityName()
        }

        val cancelButton: Button = findViewById(R.id.cancel_button_select_city)
        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun selectCityName(cityNameBeforeSelect: String) {
        val writeCityNameEditText: EditText = findViewById(R.id.writeCityNameEditText)
        writeCityNameEditText.visibility = View.INVISIBLE

        val selectCityRadioGroup: RadioGroup = findViewById(R.id.select_city_radioGroup)
        setPresetRadioButton(selectCityRadioGroup, cityNameBeforeSelect)
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

    private fun setPresetRadioButton(
        selectCityRadioGroup: RadioGroup,
        cityNameBeforeSelect: String
    ) {
        for (radioButton in selectCityRadioGroup.children) {
            if ((radioButton as AppCompatRadioButton).text == cityNameBeforeSelect) {
                radioButton.isChecked = true
                return
            }
        }
        (selectCityRadioGroup.getChildAt(selectCityRadioGroup.childCount - 1) as AppCompatRadioButton)
            .isChecked =true
        writeCityNameEditText.visibility = View.VISIBLE
    }


    private fun requestCityName() {
        val writeCityNameEditText: EditText = findViewById(R.id.writeCityNameEditText)

        val selectCityRadioGroup: RadioGroup = findViewById(R.id.select_city_radioGroup)
        val radioButtonId: Int = selectCityRadioGroup.checkedRadioButtonId
        val radioButtonCityName: RadioButton = findViewById(radioButtonId)

        when (radioButtonId) {
            R.id.city_manually_radioButton -> {
                requestCityName(writeCityNameEditText.text.toString())
            }
            else -> {
                requestCityName(radioButtonCityName.text.toString())
            }
        }
    }

    private fun requestCityName(cityNameResult: String) {
        val intentResult = Intent()
        intentResult.putExtra(Constants.CITY_NAME_SELECT, cityNameResult)
        setResult(RESULT_OK, intentResult)
        finish()
    }


//    override fun onStart() {
//        super.onStart()
//        Log.d("SelectCityActivity", "onStart")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.d("SelectCityActivity", "onResume")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.d("SelectCityActivity", "onPause")
//    }
//    override fun onStop() {
//        super.onStop()
//        Log.d("SelectCityActivity", "onStop")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d("SelectCityActivity", "onDestroy")
//    }
}