package com.amk.weatherforall.fragments

import androidx.fragment.app.Fragment
import com.amk.weatherforall.R
import kotlinx.android.synthetic.main.fragment_main.view.*

enum class FragmentsNames(val fragment: Fragment, val idFragment:Int) {


    MainFragment(
        com.amk.weatherforall.fragments.MainFragment.getInstance(), R.id.navigation_home),
    SelectCityFragment(com.amk.weatherforall.fragments.selectCityFragment.SelectCityFragment.getInstance(), R.id.navigation_change_city),
    SettingsFragment(com.amk.weatherforall.fragments.SettingsFragment.getInstance(), R.id.navigation_settings)
//    MapsFragment(com.amk.weatherforall.fragments.MapsFragment.getInstance())
//    NextWeathersFragment(com.amk.weatherforall.fragments.NextWeathersFragment
//        .getInstance(WeatherPresenter.weatherList.subList(1,WeatherPresenter.weatherList.size)))
}