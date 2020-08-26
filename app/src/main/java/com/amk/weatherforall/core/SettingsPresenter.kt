package com.amk.weatherforall.core

object SettingsPresenter {
    private const val TEMPERATURE_C = 15

    internal var isShowPressure: Boolean = false
    internal var isShowWind: Boolean = false

    internal var isShowTemperatureInC: Boolean = true

    internal val temperature: String
        get() = if (isShowTemperatureInC) "$TEMPERATURE_C C"
        else {
            "${TEMPERATURE_C.convertToF()} F"
        }

    private fun Int.convertToF() = ((this * 1.8) + 32).toInt()
}