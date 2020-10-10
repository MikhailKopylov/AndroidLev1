package com.amk.weatherforall.fragments

import androidx.fragment.app.Fragment

enum class FragmentsNames(val fragment: Fragment) {

    MainFragment(
        com.amk.weatherforall.fragments.MainFragment.getInstance()),
    SelectCityFragment(com.amk.weatherforall.fragments.selectCityFragment.SelectCityFragment.getInstance()),
    SettingsFragment(com.amk.weatherforall.fragments.SettingsFragment.getInstance()),
    MapsFragment(com.amk.weatherforall.fragments.MapsFragment.getInstance())
//    NextWeathersFragment(com.amk.weatherforall.fragments.NextWeathersFragment
//        .getInstance(WeatherPresenter.weatherList.subList(1,WeatherPresenter.weatherList.size)))
}