package com.amk.weatherforall.fragments

import androidx.fragment.app.Fragment
import com.amk.weatherforall.R

enum class FragmentsNames(val fragment: Fragment, val idFragment:Int) {


    MainFragment(
        com.amk.weatherforall.fragments.mainFragment.MainFragment.getInstance(), R.id.navigation_home),
    SelectCityFragment(com.amk.weatherforall.fragments.selectCityFragment.SelectCityFragment.getInstance(), R.id.navigation_change_city),
    SettingsFragment(com.amk.weatherforall.fragments.SettingsFragment.getInstance(), R.id.navigation_settings)
}