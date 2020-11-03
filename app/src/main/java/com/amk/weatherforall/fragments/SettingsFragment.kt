package com.amk.weatherforall.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.amk.weatherforall.R
import com.amk.weatherforall.services.Settings
import com.amk.weatherforall.viewModels.BottomNavigationViewModel


class SettingsFragment : Fragment() {

    private val settings = Settings
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
        initView(mView)
        requestResult(view)
    }

    override fun onResume() {
        super.onResume()
        initView(mView)
    }

    private fun initView(view: View) {
        selectTemperatureMode(view)

        val showWindCheckBox: CheckBox = view.findViewById(R.id.show_wind_checkBox)
        showWindCheckBox.isChecked = settings.showWind

        val showPressureCheckBox: CheckBox = view.findViewById(R.id.show_pressure_checkBox)
        showPressureCheckBox.isChecked = settings.showPressure
    }

    @SuppressLint("SetTextI18n")
    private fun selectTemperatureMode(view: View) {

        val temperatureRadioGroup: RadioGroup =
            view.findViewById(R.id.select_temperature_mode_radio_button)
        val temperatureC: RadioButton = view.findViewById(R.id.display_in_C_radioButton)
        val temperatureF: RadioButton = view.findViewById(R.id.display_in_F_radioButton)

        if (settings.temperatureC) {
            temperatureC.isChecked = true
        } else {
            temperatureF.isChecked = true
        }

        temperatureRadioGroup.setOnCheckedChangeListener { _, radioButtonId ->
            settings.previousTemperatureC = settings.temperatureC
            when (radioButtonId) {
                temperatureC.id -> settings.temperatureC = true
                temperatureF.id -> settings.temperatureC = false
            }

        }
    }

    private fun requestResult(view: View) {
        val okButton: Button = view.findViewById(R.id.ok_button_setting)
        okButton.setOnClickListener {
            val showWindCheckBox: CheckBox = view.findViewById(R.id.show_wind_checkBox)
            settings.showWind = showWindCheckBox.isChecked

            val showPressureCheckBox: CheckBox = view.findViewById(R.id.show_pressure_checkBox)
            settings.showPressure = showPressureCheckBox.isChecked

            closeFragment()

        }

        val cancelButton: Button = view.findViewById(R.id.cancel_button_setting)
        cancelButton.setOnClickListener {
            settings.temperatureC = settings.previousTemperatureC
            closeFragment()
        }
    }

    private fun closeFragment() {
        val bottomNavigationViewModel: BottomNavigationViewModel = ViewModelProviders.of(
            activity ?: return
        ).get(
            BottomNavigationViewModel::class.java
        )
        bottomNavigationViewModel.selectItemBottom(R.id.navigation_home)

        runFragments(activity ?: return, FragmentsNames.MainFragment)

    }
}