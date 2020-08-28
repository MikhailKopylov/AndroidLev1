package com.amk.weatherforall.core

interface Constants {
    companion object{
        const val CITY_NAME_SELECT = "City name activity request"
//        const val CITY_NAME_BEFORE_SELECT = "City name activity before"

        const val SETTING_SHOW_MODE_TEMPERATURE = "Show temperature in C before"
//        const val SETTING_SHOW_MODE_TEMPERATURE_AFTER = "Show temperature in C request"

        const val SETTING_SHOW_WIND = "Show wind"
        const val SETTING_SHOW_PRESSURE = "Show pressure"

        const val REQUEST_CODE_SELECT_CITY: Int = 18
        const val REQUEST_CODE_SETTING: Int = 19
    }
}