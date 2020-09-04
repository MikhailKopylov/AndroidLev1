package com.amk.weatherforall.fragments

import androidx.fragment.app.Fragment
import com.amk.weatherforall.core.WeatherPresenter

enum class FragmentsNames(val fragment: Fragment) {

    MainFragment(com.amk.weatherforall.fragments.MainFragment.getInstance()),
    SelectCityFragment(com.amk.weatherforall.fragments.SelectCityFragment.getInstance()),
    SettingsFragment(com.amk.weatherforall.fragments.SettingsFragment.getInstance()),
    NextWeathersFragment(com.amk.weatherforall.fragments.NextWeathersFragment
        .getInstance(WeatherPresenter.weatherList.subList(1,WeatherPresenter.weatherList.size)))
}