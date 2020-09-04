package com.amk.weatherforall.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amk.weatherforall.R
import com.amk.weatherforall.core.Constants
import com.amk.weatherforall.core.interfaces.StartFragment

class SettingsFragment : Fragment() {
    private var showTemperatureInC: Boolean = true

    private lateinit var mView: View

    companion object {
        fun getInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mView = view
        requestResult(view)
    }

    override fun onResume() {
        super.onResume()
        bootSettings(mView)
    }

    private fun bootSettings(view: View) {
        selectTemperatureMode(view)

        val showWindCheckBox: CheckBox = view.findViewById(R.id.show_wind_checkBox)
        showWindCheckBox.isChecked = arguments?.getBoolean(Constants.SETTING_SHOW_WIND) ?: false

        val showPressureCheckBox: CheckBox = view.findViewById(R.id.show_pressure_checkBox)
        showPressureCheckBox.isChecked =
            arguments?.getBoolean(Constants.SETTING_SHOW_PRESSURE) ?: false
    }

    @SuppressLint("SetTextI18n")
    private fun selectTemperatureMode(view: View) {
        //TODO add change temperature mode
        showTemperatureInC = arguments?.getBoolean(Constants.SETTING_SHOW_MODE_TEMPERATURE) ?: true

        val temperatureRadioGroup: RadioGroup =
            view.findViewById(R.id.select_temperature_mode_radio_button)
        val temperatureC: RadioButton = view.findViewById(R.id.display_in_C_radioButton)
        val temperatureF: RadioButton = view.findViewById(R.id.display_in_F_radioButton)

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

    private fun requestResult(view: View) {
        val okButton: Button = view.findViewById(R.id.ok_button_setting)
        okButton.setOnClickListener {
            val showWindCheckBox: CheckBox = view.findViewById(R.id.show_wind_checkBox)
            val isShowWind = showWindCheckBox.isChecked

            val showPressureCheckBox: CheckBox = view.findViewById(R.id.show_pressure_checkBox)
            val isShowPressure = showPressureCheckBox.isChecked

            val bundleResult = Bundle()
            bundleResult.putBoolean(Constants.SETTING_SHOW_MODE_TEMPERATURE, showTemperatureInC)
            bundleResult.putBoolean(Constants.SETTING_SHOW_WIND, isShowWind)
            bundleResult.putBoolean(Constants.SETTING_SHOW_PRESSURE, isShowPressure)

            (context as StartFragment).runFragments(FragmentsNames.MainFragment, bundleResult)
        }

        val cancelButton: Button = view.findViewById(R.id.cancel_button_setting)
        cancelButton.setOnClickListener {
            closeFragment()
        }
    }

    private fun closeFragment() {
        (context as StartFragment).runFragments(FragmentsNames.MainFragment, Bundle.EMPTY)
    }
}